import os


def main():
    app_env = os.getenv("APP_ENV", "development")
    print(app_env)


if __name__ == "__main__":
    main()

