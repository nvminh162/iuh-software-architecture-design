import os
from flask import Flask

app = Flask(__name__)


@app.get("/")
def index():
    instance_name = os.getenv("INSTANCE_NAME", "flask")
    return f"Hello from {instance_name}\n"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
