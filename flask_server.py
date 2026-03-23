#!/usr/bin/env python3
from flask import Flask, request, jsonify
import ssl

app = Flask(__name__)

@app.route('/api/data', methods=['GET'])
def receive_data():
    """Receive HTTPS GET request from Android app"""
    data = request.args.to_dict()
    print(f"Received GET request with data: {data}")
    
    return jsonify({
        'status': 'success',
        'message': 'Data received',
        'received_data': data
    }), 200

@app.route('/health', methods=['GET'])
def health_check():
    """Simple health check endpoint"""
    return jsonify({'status': 'healthy'}), 200

@app.route('/test', methods=['GET', 'POST'])
def test():
    """Test endpoint"""
    return jsonify({'status': 'ok', 'message': 'Test route is working'}), 200

@app.route('/message', methods=['POST'])
def receive_message():
    """Receive a single string in the request body"""
    data = request.get_data(as_text=True)
    print(f"Received message: {data}")
    return jsonify({'status': 'ok', 'received': data}), 200

if __name__ == '__main__':
    # Use self-signed certificate and key
    ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
    ssl_context.load_cert_chain('cert.pem', 'key.pem')
    
    print("Starting HTTPS server on https://0.0.0.0:5000")
    print("Endpoints:")
    print("  - GET https://YOUR_IP:5000/api/data?param1=value1&param2=value2")
    print("  - GET https://YOUR_IP:5000/health")
    print("\nNote: Self-signed certificate - ignore SSL warnings on Android")
    
    app.run(host='0.0.0.0', port=5000, ssl_context=ssl_context, debug=False)
