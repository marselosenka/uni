FROM python:3.9-slim

WORKDIR /app

RUN pip install --no-cache-dir kopf kubernetes Jinja2 PyYAML

COPY controller.py .

CMD ["kopf", "run", "--standalone", "/app/controller.py", "--verbose"]