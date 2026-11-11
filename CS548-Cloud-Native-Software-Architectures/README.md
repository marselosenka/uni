# CS548 — Docker and Kubernetes Coursework

![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?logo=kubernetes&logoColor=white)
![Helm](https://img.shields.io/badge/Helm-0F1689?logo=helm&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?logo=python&logoColor=white)
![Flask](https://img.shields.io/badge/Flask-000000?logo=flask&logoColor=white)
![Django](https://img.shields.io/badge/Django-092E20?logo=django&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?logo=nginx&logoColor=white)

Coursework repository covering containerization, Kubernetes workload management, application scaling, Helm packaging, and Kubernetes API extensions.

The assignments form a progressive cloud-native workflow: starting with Docker fundamentals, moving applications into Kubernetes, adding autoscaling and reusable Helm charts, and finally extending Kubernetes with custom resources, an operator, and an admission webhook.

## Coursework overview

| Folder | Topic | Main technologies |
| --- | --- | --- |
| [`Assignment_1/`](Assignment_1/) | Docker fundamentals and containerized Django | Docker, Nginx, Django, GitHub Actions |
| [`Assignment_2/`](Assignment_2/) | Kubernetes workloads, storage, and routing | Kubernetes, Minikube, Jobs, CronJobs, Services, Ingress |
| [`Assignment_3_4/`](Assignment_3_4/) | Scaling and extending Kubernetes | HPA, Helm, CRDs, Kopf, Flask, cert-manager |

## Repository structure

```text
CS548/
├── Assignment_1/
│   ├── django/
│   ├── generated/
│   ├── .github/workflows/
│   └── CS-548 - Assignment I - Docker.pdf
├── Assignment_2/
│   ├── hy548_hw2/
│   │   ├── part-a/
│   │   ├── part-b/
│   │   └── part-c/
│   └── CS-548 - Assignment II - Kubernetes.pdf
├── Assignment_3_4/
│   ├── [HW3 & HW4 submission]/
│   │   ├── HW3/
│   │   └── HW4/
│   ├── CS-548 - Assignment III - Scaling.pdf
│   └── CS-548 - Assignment IV - Extending.pdf
└── README.md
```

## Assignment progression

```text
Docker containers
       │
       ▼
Custom images and persistent storage
       │
       ▼
Kubernetes Pods, Jobs, and Deployments
       │
       ▼
Services, storage, and Ingress
       │
       ▼
Resource limits and autoscaling
       │
       ▼
Reusable Helm charts
       │
       ▼
CRDs, operators, and admission webhooks
```

# Assignment 1 — Docker

Assignment 1 introduces container lifecycle management and image creation.

## Topics covered

- Pulling and comparing standard and Alpine Nginx images
- Creating, inspecting, stopping, restarting, and removing containers
- Executing commands inside running containers
- Copying files between containers and the host
- Understanding ephemeral container filesystems
- Persisting Nginx content with bind mounts
- Building a custom Django image
- Persisting SQLite data with a named volume
- Configuring Django debug behavior with environment variables
- Publishing an image to Docker Hub
- Building and publishing automatically through GitHub Actions

## Django container

The application under `Assignment_1/django/` uses:

- `python:3.13.2-bookworm`
- Django 5.1.6
- SQLite under `/data`
- `DJANGO_DEBUG` for debug-mode selection
- A startup script that runs migrations and starts the development server

The GitHub Actions workflow builds the Django image and publishes `marselosenka/hy548-django:latest` using repository secrets.

## Navigation

- [Assignment 1 submission notes](Assignment_1/README.MD)
- [Assignment 1 brief](<Assignment_1/CS-548 - Assignment I - Docker.pdf>)

# Assignment 2 — Kubernetes

Assignment 2 transfers the container workflows into Kubernetes and introduces declarative orchestration.

## Part A — Pod operations

- Defines an Nginx Pod with YAML
- Uses port forwarding, logs, and `kubectl exec`
- Copies and modifies files inside the Pod
- Demonstrates Pod deletion and recreation

## Part B — Batch workloads and storage

- ConfigMap-hosted download scripts
- One-off Kubernetes Jobs
- Scheduled CronJobs
- PersistentVolume and PersistentVolumeClaim usage
- Shared website data between download and Nginx containers
- Init containers that prepare content before an application starts
- `emptyDir` lifecycle behavior

## Part C — Deployments and Ingress

- Custom Nginx image with a configurable `TARGET_URL`
- Replicated Kubernetes Deployments
- ClusterIP and LoadBalancer Services
- Path-based Nginx Ingress routing
- Separate `/csd` and `/math` routes backed by different website mirrors

## Navigation

- [Assignment 2 submission notes](Assignment_2/hy548_hw2/README.MD)
- [Assignment 2 brief](<Assignment_2/CS-548 - Assignment II - Kubernetes.pdf>)

# Assignment 3 — Scaling Kubernetes

Assignment 3 focuses on workload resource management, horizontal scaling, benchmarking, and Helm.

## Resource management and HPA

The `hello-kubernetes` workload defines CPU and memory requests/limits and uses an `autoscaling/v2` HorizontalPodAutoscaler.

| Setting | Value |
| --- | --- |
| CPU request | `50m` |
| Memory request | `64Mi` |
| CPU limit | `200m` |
| Memory limit | `256Mi` |
| CPU target | 80% average utilization |
| Replica range | 1–8 |

The assignment uses `hey` to compare low- and high-concurrency HTTP workloads and records the HPA scaling the Deployment under load.

## Helm chart

The reusable `hello-chart` packages:

- Deployment
- Service
- Ingress
- HorizontalPodAutoscaler

Its values configure the response message, route path, optional CPU limit, and maximum replica count.

## Navigation

- [Assignment 3 submission notes](Assignment_3_4/%5B%20HW3%20%26%20HW4%20%5D%20-%20CSDP1491%20Marsel%20Senka/HW3/README.md)
- [Assignment 3 brief](<Assignment_3_4/CS-548 - Assignment III - Scaling.pdf>)

# Assignment 4 — Extending Kubernetes

Assignment 4 explores Kubernetes as an extensible control-plane platform.

## Fruit CustomResourceDefinition

Defines a namespaced `Fruit` resource under `hy548.csd.uoc.gr/v1`, with fields for origin, count, and weight.

## Greeting operator

A Python controller built with Kopf watches `Greeting` resources. For each new Greeting, it renders and creates:

- A Deployment running `hello-kubernetes`
- A ClusterIP Service
- Owner references through `kopf.adopt`

The deployment includes a ServiceAccount and RBAC rules for custom resources, Deployments, and Services.

## Mutating admission webhook

A Flask-based HTTPS webhook injects `custom-label=true` into newly created Pods and Deployments in explicitly enabled namespaces.

The deployment includes:

- A dedicated namespace
- A self-signed cert-manager Issuer
- TLS Certificate and Secret
- Service and controller Deployment
- `MutatingWebhookConfiguration`
- Base64-encoded JSON Patch responses

## Navigation

- [Assignment 4 submission notes](Assignment_3_4/%5B%20HW3%20%26%20HW4%20%5D%20-%20CSDP1491%20Marsel%20Senka/HW4/README.MD)
- [Assignment 4 brief](<Assignment_3_4/CS-548 - Assignment IV - Extending.pdf>)

## Prerequisites

Different assignments require different subsets of the following tools:

- Docker and Docker Hub
- Python 3
- `kubectl`
- Minikube or another Kubernetes cluster
- Metrics Server
- Nginx Ingress Controller
- Helm 3
- `hey` for HTTP load testing
- cert-manager

## Validation summary

The repository was evaluated before this overview was written:

- All 13 plain Kubernetes YAML files parsed successfully.
- Both Python controllers passed syntax compilation.
- Kubernetes selectors, Services, and referenced workloads were reviewed for consistency.
- HPA targets and resource settings were checked.
- CRD groups and sample custom resources were checked for alignment.
- Webhook Service, certificate DNS name, and admission configuration were checked together.

Helm templates require rendering through `helm template` or installation into a cluster; they are not plain YAML until rendered.

## Scope and limitations

- The manifests target coursework and local Kubernetes environments.
- `hostPath` storage is suitable for single-node demonstrations, not portable production deployments.
- Several images use the mutable `latest` tag.
- The Django container uses the development server.
- The Greeting operator implements creation handling but not full update/delete reconciliation.
- The admission webhook depends on cert-manager and can block matching resource creation while unavailable because it uses `failurePolicy: Fail`.

## Security notice

One original Assignment 1 submission note contains an exposed Docker Hub token. Revoke and replace that token before publishing this repository. Removing it from the current file alone would not remove it from Git history.

## Author

**Marsel Senka**  
CS548 coursework
