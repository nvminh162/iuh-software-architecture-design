FROM python:3.11-slim AS base

WORKDIR /app

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

FROM base AS dev
ENV FLASK_APP=app.py
ENV FLASK_ENV=development
CMD ["flask", "run", "--host=0.0.0.0", "--port=8000"]

FROM base AS prod
CMD ["gunicorn", "-b", "0.0.0.0:8000", "app:app"]
