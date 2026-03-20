from flask import Flask, jsonify

app = Flask(__name__)


@app.get("/")
def home():
    return jsonify({"message": "CI/CD demo app is running"})


@app.get("/health")
def health():
    return jsonify({"status": "ok"})
