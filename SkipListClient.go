package SkipList

/*just a demo*/

//import (
//	"context"
//	"log"
//	"time"
//)
//
//func NewSkipListClient(conn *grpc.ClientConn) *SkipListClient {
//	return &SkipListClient{
//		client: pb.NewSchedulerServiceClient(conn),
//	}
//}
//
//func (c *SkipListClient) GetOptimalNode(minScore int32) (string, error) {
//	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
//	defer cancel()
//
//	req := &pb.GetOptimalNodeRequest{MinScore: minScore}
//	res, err := c.client.GetOptimalNode(ctx, req)
//	if err != nil {
//		return "", err
//	}
//	return res.NodeName, nil
//}
//
//func (c *SkipListClient) UpdateNodeStatus(nodeName string, score int32) (bool, error) {
//	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
//	defer cancel()
//
//	req := &pb.UpdateNodeStatusRequest{NodeName: nodeName, Score: score}
//	res, err := c.client.UpdateNodeStatus(ctx, req)
//	if err != nil {
//		return false, err
//	}
//	return res.Success, nil
//}
//
//// 定义 NodeScore 结构体
//type NodeScore struct {
//	NodeName string
//	Score    int32
//}
//
//// 定义评分插件接口
//type ScorePlugin interface {
//	Score(ctx context.Context, state *CycleState, pod *Pod, nodeName string) (int32, error)
//}
//
//type CycleState struct{}
//
//type Pod struct{}
//
//// 调用评分插件进行评分并返回 NodeScore 结构体
//func updateNodeAndScore(ctx context.Context, plugin ScorePlugin, state *CycleState, pod *Pod, nodeName string) (NodeScore, error) {
//	nodeScore := NodeScore{
//		NodeName: nodeName,
//		Score:    0,
//	}
//
//	score, err := plugin.Score(ctx, state, pod, nodeName)
//	if err != nil {
//		return NodeScore{}, err
//	}
//	nodeScore.Score = score
//
//	return nodeScore, nil
//}
//
//// 使用评分结果更新 SkipList 节点
//func updateSkipListNode(ctx context.Context, client *SkipListClient, plugin ScorePlugin, state *CycleState, pod *Pod, nodeName string) error {
//	nodeScore, err := updateNodeAndScore(ctx, plugin, state, pod, nodeName)
//	if err != nil {
//		return err
//	}
//
//	success, err := client.UpdateNodeStatus(nodeScore.NodeName, nodeScore.Score)
//	if err != nil || !success {
//		return err
//	}
//
//	log.Printf("Node %s updated with score %d\n", nodeScore.NodeName, nodeScore.Score)
//	return nil
//}
//
//func main() {
//	conn, err := grpc.Dial(address, grpc.WithInsecure())
//	if err != nil {
//		log.Fatalf("did not connect: %v", err)
//	}
//	defer conn.Close()
//
//	client := NewSkipListClient(conn)
//	state := &CycleState{}
//	pod := &Pod{}
//	nodeName := "example-node"
//	minScore := int32(50) // 设置最低分数
//
//	var plugin ScorePlugin // 初始化具体的评分插件
//
//	nodeName, err = client.GetOptimalNode(minScore)
//	if err != nil {
//		log.Fatalf("could not get optimal node: %v", err)
//	}
//	log.Printf("Optimal node: %s", nodeName)
//
//	err = updateSkipListNode(context.Background(), client, plugin, state, pod, nodeName)
//	if err != nil {
//		log.Fatalf("could not update skip list node: %v", err)
//	}
//}
