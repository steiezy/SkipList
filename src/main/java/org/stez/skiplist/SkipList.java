package org.stez.skiplist;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

/*
 *  TODO: cold node(a node before the same score's index)
 *   possible solution: Nodes with the same score only maintain one index,
 *   and when deleting nodes, check if there are nodes with the same score.
 *   If there are, inherit the index of the deleted node to the next node with the same score.
 * */
public class SkipList {
    static Comparator<Integer> comparator = (a, b) -> Integer.compare(b, a);

    //to avoid another Indexes
    private final ConcurrentHashMap<String, Integer> ScoreMap = new ConcurrentHashMap<>();
    private transient Index head;

    public void printTree() {
        Index h;
        if ((h = head) != null) {
            for (Index p = h; p != null; p = p.down) {
                for (Index n = p; n != null; n = n.right) {
                    if (n.node != null) {
                        System.out.print(n.node.score + " " + n.node.name + " | ");
                    }
                }
                System.out.println();
            }
            System.out.print("base list:");
            Node base = h.node;
            for (Node n = base; n != null; n = n.next) {
                System.out.print(n.score + " " + n.name + " | ");
            }
            System.out.println();
        }
    }


    static final class Node {
        String name; // node name
        Integer score;// node score
        Node next;

        Node(String name, Integer score, Node next) {
            this.name = name;
            this.score = score;
            this.next = next;
        }
    }

    /**
     * Index nodes represent the levels of the skip list.
     */
    static final class Index {
        final Node node;  // currently, never detached
        final Index down;
        Index right;

        Index(Node node, Index down, Index right) {
            this.node = node;
            this.down = down;
            this.right = right;
        }
    }

    // VarHandle mechanics
    private static final VarHandle HEAD;
    private static final VarHandle NEXT;
    private static final VarHandle VAL;
    private static final VarHandle RIGHT;

    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            HEAD = l.findVarHandle(SkipList.class, "head",
                    Index.class);
            NEXT = l.findVarHandle(Node.class, "next", Node.class);
            VAL = l.findVarHandle(Node.class, "name", String.class);
            RIGHT = l.findVarHandle(Index.class, "right", Index.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public String getOptimalNode(Integer minScore) {
        if (minScore == null || minScore < 0)
            throw new IllegalArgumentException();
        Node head = baseHead();
        if (head == null || head.next == null)
            return null;//or "null"
        Integer score = findScoreThatFits(minScore, head.next.score);
        return score == -1 ? null : doGet(score);
    }

    private Integer findScoreThatFits(Integer minScore, Integer headScore) {
        if (minScore > headScore) {
            return -1;
        }
        return ThreadLocalRandom.current().nextInt(minScore, headScore + 1);
    }

    public String doGet(Integer score) {
        Index q;
        VarHandle.acquireFence();
        if (score == null) {
            throw new NullPointerException();
        }
        String result = null;
        if ((q = head) != null) {
            outer:
            for (Index r, d; ; ) {
                while ((r = q.right) != null) {
                    Node p;
                    Integer k;
                    String v;
                    int c;
                    if ((p = r.node) == null || (k = p.score) == null || (v = p.name) == null)
                        RIGHT.compareAndSet(q, r, r.right);
                    else if ((c = cpr(score, k)) > 0)
                        q = r;
                    else if (c == 0) {
                        //can add more randomness by checking the next node,but it's not too necessary
                        result = v;
                        break outer;
                    } else
                        break;
                }
                if ((d = q.down) != null)
                    q = d;
                else {
                    Node b, n;
                    if ((b = q.node) != null) {
                        while ((n = b.next) != null) {
                            String v;
                            int c;
                            Integer k = n.score;
                            if ((v = n.name) == null || k == null || (c = cpr(score, k)) > 0)
                                b = n;
                            else {
                                if (c == 0)
                                    result = v;
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return result;
    }


    public boolean updateNodeStatus(String nodeName, int score) {
        if (nodeName == null)
            throw new NullPointerException();
        //serial update
        Integer prevScore = ScoreMap.get(nodeName);
        if (prevScore != null){
            if (prevScore == score) {
                return true;
            }else doRemove(nodeName, prevScore);
        }
        doPut(score, nodeName);
        ScoreMap.put(nodeName, score);

        //just for checking the connection
        return true;
    }

    private String doPut(Integer score, String name) {
        if (score == null)
            throw new NullPointerException();
        for (; ; ) {
            Index h;
            Node b;
            VarHandle.acquireFence();
            int levels = 0; // number of levels descended
            if ((h = head) == null) { // try to initialize
                Node base = new Node(null, null, null);
                h = new Index(base, null, null);
                b = (HEAD.compareAndSet(this, null, h)) ? base : null;
            } else {
                for (Index q = h, r, d; ; ) { // count while descending
                    while ((r = q.right) != null) {
                        Node p;
                        Integer k;
                        if ((p = r.node) == null || (k = p.score) == null || p.name == null)
                            RIGHT.compareAndSet(q, r, r.right);
                        else if (cpr(score, k) > 0)
                            q = r;
                        else
                            break;
                    }
                    if ((d = q.down) != null) {
                        ++levels;
                        q = d;
                    } else {
                        b = q.node;
                        break;
                    }
                }
            }
            if (b != null) {
                Node z = null; // new node, if inserted
                for (; ; ) { // find insertion point
                    Node n, p;
                    Integer k;
                    String v;
                    int c;
                    if ((n = b.next) == null) {// at end of list
                        c = -1;
                    } else if ((k = n.score) == null)
                        break; // can't append; restart
                    else if ((v = n.name) == null) {
                        unlinkNode(b, n);
                        c = 1;
                    } else if ((c = cpr(score, k)) > 0)
                        b = n;
                    else if (c == 0) {
                        int cmp;
                        while (n != null && c == 0) { // Traverse nodes with the same score
                            if ((cmp = name.compareTo(n.name)) > 0) { // avoid duplicate names
                                b = n; // move to the next node
                                n = n.next;
                                if (n != null) {
                                    k = n.score;
                                    c = cpr(score, k);
                                } else {
                                    c = -1; // end of list
                                }
                            } else if (cmp == 0) {
                                return v; // name already exists
                            } else {
                                c = -1;
                                break; // insert here since name is smaller
                            }
                        }
                    }

                    if (c < 0 && NEXT.compareAndSet(b, n, p = new Node(name, score, n))) {
                        z = p;
                        break;
                    }
                }

                if (z != null) {
                    int lr = ThreadLocalRandom.current().nextInt();
                    if ((lr & 0x3) == 0) { // add indices with 1/4 prob
                        int hr = ThreadLocalRandom.current().nextInt();
                        long rnd = ((long) hr << 32) | ((long) lr & 0xffffffffL);
                        int skips = levels; // levels to descend before add
                        Index x = null;
                        for (; ; ) { // create at most 62 indices
                            x = new Index(z, x, null);
                            if (rnd >= 0L || --skips < 0)
                                break;
                            else
                                rnd <<= 1;
                        }
                        if (addIndices(h, skips, x) && skips < 0 && head == h) { // try to add new level
                            Index hx = new Index(z, x, null);
                            Index nh = new Index(h.node, h, hx);
                            HEAD.compareAndSet(this, h, nh);
                        }
                        if (z.name == null) // deleted while adding indices
                            findPredecessor(score); // clean
                    }
                    return null;
                }
            }
        }
    }

    static boolean addIndices(Index q, int skips, Index x) {
        Node z;
        Integer key;
        if (x != null && (z = x.node) != null && (key = z.score) != null && q != null) { // hoist checks
            boolean retrying = false;
            for (; ; ) { // find splice point
                Index r, d;
                int c;
                if ((r = q.right) != null) {
                    Node p;
                    Integer k;
                    if ((p = r.node) == null || (k = p.score) == null || p.name == null) {
                        RIGHT.compareAndSet(q, r, r.right);
                        c = 0;
                    } else if ((c = cpr(key, k)) > 0) {
                        q = r;
                    } else if (c == 0) {
                        //don't add to avoid index chaos
                        break;
                    } else {
                        c = -1;
                    }
                } else {
                    c = -1;
                }

                if (c < 0) {
                    if ((d = q.down) != null && skips > 0) {
                        --skips;
                        q = d;
                    } else if (d != null && !retrying && !addIndices(d, 0, x.down)) {
                        break;
                    } else {
                        x.right = r;
                        if (RIGHT.compareAndSet(q, r, x)) {
                            return true;
                        } else {
                            retrying = true; // re-find splice point
                        }
                    }
                }
            }
        }
        return false;
    }


    final Integer doRemove(String name, Integer score) {
        if (score == null)
            throw new NullPointerException();
        Integer result = null;
        Node b;
        String v = null;//inevitable to make sure cas
        outer:
        while ((b = findPredecessor(score)) != null &&
                result == null) {
            inner:
            for (; ; ) {
                Node n;
                Integer k;
                int c;
                if ((n = b.next) == null)
                    break outer;
                else if ((k = n.score) == null)
                    break;
                else if ((v = n.name) == null)
                    unlinkNode(b, n);
                else if ((c = cpr(score, k)) > 0)
                    b = n;
                else if (c < 0)
                    break outer;
                else { // score is equal, now check name
                    for (; n != null && n.score.equals(score); b = n, n = n.next) { // Traverse nodes with the same score
                        if (name.equals((v = n.name))) {
                            if (VAL.compareAndSet(n, v, null)) {
                                result = k;
                                unlinkNode(b, n);
                                System.out.println("removed " + name + " successfully");
                                break inner; //clean up node
                            } else continue inner;//retry
                        }
                        // Move to the next node
                    }
                    // If the node is not found, break the outer loop
                    break outer;
                }
            }
        }
        if (result != null) {
            tryReduceLevel();
        }
        return result;
    }

    private Node findPredecessor(Integer key) {
        Index q;
        VarHandle.acquireFence();
        if ((q = head) == null || key == null)
            return null;
        else {
            for (Index r, d; ; ) {
                while ((r = q.right) != null) {
                    Node p;
                    Integer k;
                    if ((p = r.node) == null || (k = p.score) == null ||
                            p.name == null)  // unlink index to deleted node
                        RIGHT.compareAndSet(q, r, r.right);
                    else if (cpr(key, k) > 0)
                        q = r;
                    else
                        break;
                }
                if ((d = q.down) != null)
                    q = d;
                else
                    return q.node;
            }
        }
    }

    private void tryReduceLevel() {
        Index h, d, e;
        if ((h = head) != null && h.right == null &&
                (d = h.down) != null && d.right == null &&
                (e = d.down) != null && e.right == null &&
                HEAD.compareAndSet(this, h, d) &&
                h.right != null)   // recheck
            HEAD.compareAndSet(this, d, h);  // try to backout
    }


    static int cpr(int x, int y) {
        return comparator.compare(x, y);
    }

    /**
     * Returns the header for base node list, or null if uninitialized
     */
    final Node baseHead() {
        Index h;
        VarHandle.acquireFence();
        return ((h = head) == null) ? null : h.node;
    }

    static void unlinkNode(Node b, Node n) {
        if (b != null && n != null) {
            Node f, p;
            for (; ; ) {
                if ((f = n.next) != null && f.score == null) {
                    p = f.next;               // already marked
                    break;
                } else if (NEXT.compareAndSet(n, f,
                        new Node(null, null, f))) {
                    p = f;                    // add marker
                    break;
                }
            }
            NEXT.compareAndSet(b, n, p);
        }
    }

}
