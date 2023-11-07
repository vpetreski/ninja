# Ninja 🥷

Task instruction is [here](INSTRUCTIONS.md).

## Requirements

- JDK 17
- Docker
- [Curl](https://curl.se)
- [Just](https://github.com/casey/just)

## Usage

Start the service with `just run`.

Examples:

```shell
# Create Service
curl -w '\n' --location 'http://localhost/services/' \
--header 'Content-Type: application/json' \
--data '{
    "type" : "Foo",
    "price" : 100
}'

# Find Service
curl -w '\n' --location 'http://localhost/services/1'

# Delete Service
curl -w '\n' --location --request DELETE 'http://localhost/services/2'

# Create Device
curl -w '\n' --location 'http://localhost/devices/' \
--header 'Content-Type: application/json' \
--data '{
    "name" : "Dev",
    "type" : "Mac"    
}'

# Find Device
curl -w '\n' --location 'http://localhost/devices/1'

# Delete Device
curl -w '\n' --location --request DELETE 'http://localhost/devices/2'

# Add Service to Device
curl -w '\n' --location 'http://localhost/devices/1/services' \
--header 'Content-Type: application/json' \
--data '{
    "serviceId" : 1
}'

# Remove Service from Device
curl -w '\n' --location --request DELETE 'http://localhost/devices/2/services' \
--header 'Content-Type: application/json' \
--data '{
    "serviceId" : 2
}'

# Calculate cost for specific Device
curl -w '\n' --location 'http://localhost/devices/1/cost'

# Calculate total cost
curl -w '\n' --location 'http://localhost/devices/cost'
```

Run tests with `just test`.

## Improvements

- Improve OpenAPI / Swagger docs
- More tests