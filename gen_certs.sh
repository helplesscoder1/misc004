#!/bin/bash
# Generate self-signed certificate and key for HTTPS

openssl req -x509 -newkey rsa:4096 -nodes -out cert.pem -keyout key.pem -days 365 \
  -subj "/C=US/ST=State/L=City/O=Organization/CN=localhost"

echo "Certificate and key generated:"
echo "  cert.pem"
echo "  key.pem"
