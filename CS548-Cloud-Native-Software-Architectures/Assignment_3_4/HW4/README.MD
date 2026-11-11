Senka Marsel CSDP1491

# Requirement 1.

* `kubectl apply -f fruit.yaml` , to register the fruit kind with the kubernetes api
* same for apple.yaml
* `kubectl get fruit apple -o yaml` To return the instance.
*  `kubectl get fruits` to get that list, 

# Requirement 2.
* `docker build -t marselosenka/greeting-controller:1.0 .` 
* `docker push   marselosenka/greeting-controller:1.0`
* `kubectl apply -f greeting-crd.yaml`
* `kubectl apply -f greeting-controller.yaml`
* `kubectl apply -f hello-world.yaml`
* confirmed by the output,
* ``` greeting.hy548.csd.uoc.gr/hello-world created
  greeting.hy548.csd.uoc.gr/hello-to-all created 

* for the verification , 
* `kubectl get pods`
* `kubectl get greetings`
* `kubectl get deployments`
* `kubectl get services`
* `kubectl logs deployment/greeting-controller`
* `kubectl port-forward svc/hello-world 8080:8080` on a separate terminal
* `curl http://127.0.0.1:8080`


# Requirement 3.
* `kubectl apply -f webhook.yaml`

* for verification i ran:
  `kubectl get issuer -n custom-label-injector`
  `kubectl get certificate -n custom-label-injector`
  `kubectl get secret -n custom-label-injector`
  `kubectl get pods -n custom-label-injector`

* created a test namespace and enabled the webhook:
  `kubectl create namespace webhook-test`
  `kubectl label namespace webhook-test custom-label-injector=enabled`

* checked the pod labels:
  `kubectl get pod testpod -n webhook-test --show-labels`
  `kubectl get pod testpod -n webhook-test -o yaml`
* verified that the label `custom-label=true` was automatically injected by the webhook

* tested with a deployment:
  `kubectl create deployment testdeploy --image=nginx -n webhook-test`
  `kubectl get deployment testdeploy -n webhook-test --show-labels`
  `kubectl get deployment testdeploy -n webhook-test -o yaml`