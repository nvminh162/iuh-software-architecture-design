```
workspace "ElectroShop E-Commerce" "System Context Diagram for an electronics e-commerce platform" {

    !identifiers hierarchical

    model {
        customer = person "Customer" "End user who browses and purchases electronic devices online"
        admin = person "Administrator" "Manages products, orders, inventory, and promotions"

        ecommerce = softwareSystem "ElectroShop E-Commerce Platform" "Online platform for selling phones, laptops, PCs, and electronic accessories"

        paymentGateway = softwareSystem "Payment Gateway" "Handles online payments (VNPay, Stripe, PayPal)"
        shippingService = softwareSystem "Shipping Service" "Handles delivery and shipment tracking"
        notificationService = softwareSystem "Notification Service" "Sends email and SMS notifications"
        authService = softwareSystem "Authentication Provider" "Third-party authentication (Google OAuth)"
        supplierSystem = softwareSystem "Supplier System" "Provides product and inventory data"

        customer -> ecommerce "Browses products, places orders, makes payments"
        admin -> ecommerce "Manages system data and operations"

        ecommerce -> paymentGateway "Processes payments"
        ecommerce -> shippingService "Requests delivery and tracking"
        ecommerce -> notificationService "Sends order confirmations and alerts"
        ecommerce -> authService "Authenticates users"
        ecommerce -> supplierSystem "Synchronizes product and inventory data"
    }

    views {
        systemContext ecommerce {
            include *
            autolayout lr
            title "C4 - System Context Diagram | ElectroShop E-Commerce Platform"
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

            relationship "*" {
                thickness 4
                color "#707070"
            }
        }
    }
}

```