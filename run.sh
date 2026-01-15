#!/bin/bash

echo "🏠 Real Estate Marketplace - Setup Script"
echo "=========================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "✓ Java $JAVA_VERSION detected"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

echo "✓ Maven detected"
echo ""

# Check for Google Maps API Key
echo "📍 Checking Google Maps API Key configuration..."
if grep -q "YOUR_GOOGLE_MAPS_API_KEY_HERE" src/main/resources/application.properties; then
    echo "⚠️  Warning: Google Maps API Key not configured!"
    echo "   Please edit src/main/resources/application.properties"
    echo "   and replace YOUR_GOOGLE_MAPS_API_KEY_HERE with your actual API key"
    echo ""
    echo "   To get an API key:"
    echo "   1. Go to https://console.cloud.google.com/"
    echo "   2. Create a new project or select existing"
    echo "   3. Enable 'Maps JavaScript API'"
    echo "   4. Create credentials (API Key)"
    echo ""
    read -p "   Continue anyway? (y/n) " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
else
    echo "✓ Google Maps API Key configured"
fi

echo ""
echo "🔨 Building the application..."
mvn clean install -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi

echo ""
echo "✓ Build successful!"
echo ""
echo "🚀 Starting the application..."
echo "   The application will be available at: http://localhost:8080"
echo "   Press Ctrl+C to stop the server"
echo ""

mvn spring-boot:run
