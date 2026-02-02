```
workspace "ElectroShop E-Commerce" "C4 Container Diagram for an electronics e-commerce platform" {

    !identifiers hierarchical

    model {
        customer = person "Customer" "Browses and purchases electronic devices"
        admin = person "Administrator" "Manages products, orders, and inventory"

        ecommerce = softwareSystem "ElectroShop E-Commerce Platform" "Online platform for selling electronic devices" {

            webApp = container "Web Application" {
                description "Provides web-based user interface for customers and administrators"
                technology "React / Next.js"
            }

            mobileApp = container "Mobile Application" {
                description "Mobile app for shopping and order tracking"
                technology "React Native"
            }

            backendApi = container "Backend API" {
                description "Handles business logic, authentication, orders, and payments"
                technology "Spring Boot / Node.js"
            }

            database = container "Relational Database" {
                description "Stores users, products, orders, and payments"
                technology "PostgreSQL / MySQL"
            }

            cache = container "Cache" {
                description "Caches frequently accessed data"
                technology "Redis"
            }

            messageBroker = container "Message Broker" {
                description "Handles asynchronous processing"
                technology "RabbitMQ / Kafka"
            }

            searchEngine = container "Search Engine" {
                description "Provides full-text search for products"
                technology "Elasticsearch"
            }
        }

        customer -> ecommerce.webApp "Uses" "HTTPS"
        customer -> ecommerce.mobileApp "Uses" "HTTPS"
        admin -> ecommerce.webApp "Manages system via browser"

        ecommerce.webApp -> ecommerce.backendApi "Calls REST APIs" "JSON/HTTPS"
        ecommerce.mobileApp -> ecommerce.backendApi "Calls REST APIs" "JSON/HTTPS"

        ecommerce.backendApi -> ecommerce.database "Reads from and writes to" "JDBC"
        ecommerce.backendApi -> ecommerce.cache "Reads/Writes cached data"
        ecommerce.backendApi -> ecommerce.messageBroker "Publishes domain events"
        ecommerce.backendApi -> ecommerce.searchEngine "Indexes and searches products"
    }

    views {
        container ecommerce {
            include *
            autolayout lr
            title "C4 - Container Diagram | ElectroShop E-Commerce Platform"
        }

        styles {
            element "Person" {
                shape person
                background "#08427b"
                color "#ffffff"
            }

            element "Software System" {
                shape roundedbox
                background "#1168bd"
                color "#ffffff"
            }

            element "Container" {
                shape roundedbox
                background "#438dd5"
                color "#ffffff"
            }

            relationship "*" {
                thickness 4
                color "#707070"
            }
        }
    }
}

```