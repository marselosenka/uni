FROM python:3.9-slim

WORKDIR /app

RUN pip install --no-cache-dir Flask==2.0.3 "Werkzeug>=2.2,<3.0" jsonpatch==1.32

COPY controller.py .

CMD ["python", "controller.py"]