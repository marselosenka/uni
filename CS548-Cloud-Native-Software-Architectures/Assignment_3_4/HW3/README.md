Senka Marsel CSDP1491

# Requirement 1.

(a)
* Added resource limits to the container in the Deployment manifest:
* `cpu: "200m"` (20% of 1 CPU core, since 1 core = 1000 millicores)
* `memory: "256Mi"` (256MB RAM)
* added requests.cpu "50m" , required by kubernete so the hpa cvan calculate cpu utilization correctly

(b)
* Added a HorizontalPodAutoscaler (HPA) manifest that monitors average CPU usage across all pods in the Deployment and automatically scales 
* when it exceeds 80%.

* Additionally I had to run `minikube addons enable ingress` . Then `curl http://192.168.49.2/hello` 

(Question)
* To run benchmarks the question, I installed hey, 
* `./hey.exe -n 1000 -c 1 http://127.0.0.1/hello` 1 client, 1k requests
* `./hey.exe -n 20000 -c 100 http://127.0.0.1/hello` 100 clients, 20k requests
* on 1 client, theres 16.36 requests/sec , whereas on 100 clients, 180.13 requests/sec. 
* proves that autoscaler succesfully scaled from 1 to 8 replicas.

# Requirement 2.

(a)
* Installed Helm
* the string to return can be found inside templates/deployments.yaml. Helm takes the string inside ``--set message = ....`and 
* injectsit into the deployment environment variables
(b)
* Can be found in templates/ingress.yaml
* By `path="goodbye"` i told the kubernetes ingress controller to route traffic from the specific url to my service.
(c)
* Can be found in `templates/deployment.yaml` under resources
* the IF block basically acts as if i give it a value, it adds the limits section to the yaml, if i leave it empty, it omits it entirely.
(d)
* Can be found in templates/hpa.yaml
* When set to 20, Helm rweplaces that placeholder with the number 20, which tells Kubernetes autoscaler to allow the deployment to scale
* up to 20 pods based on CPU usage.

* Command :` & helm install goodbye ./hello-chart `
 ` --set message="Goodbye world!" `
 ` --set path="/goodbye"`
 ` --set cpuLimit="250m"`
 ` --set maxReplicas=20`

 # Requirement 3
 
 * `helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx` 
 * `helm repo update`
 * `helm install ingress-nginx ingress-nginx/ingress-nginx ` To deploy the ngix controller into my cluster
 * We had to rewrite the annotation.