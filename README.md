# SkipList for Kubernetes Scheduler

This project aims to redefine a scorePlugin for the Kubernetes scheduler to reduce value conflicts.

## Problem Statement

In the current Kubernetes scheduler, there might be conflicts in the scoring mechanism. This project aims to address this issue.

## possible Solution

The possible solution is to use the upper 16 bits for the score and the lower 16 bits to avoid conflict.

## Build

This project uses Java and Maven for building the application. To build the project, use the following command:

```bash
mvn clean install