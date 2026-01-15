#!/bin/bash

# 🏠 Real Estate Marketplace - First Time Setup Guide
# This script will guide you through the initial setup

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║                                                            ║"
echo "║     🏠 Real Estate Marketplace - Setup Wizard            ║"
echo "║                                                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Check Java
echo "🔍 Step 1: Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "❌ ERROR: Java is not installed"
    echo ""
    echo "Please install Java 17 or higher:"
    echo "  • Download from: https://adoptium.net/"
    echo "  • Or use Homebrew: brew install openjdk@17"
    echo ""
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ ERROR: Java 17 or higher is required"
    echo "   Current version: Java $JAVA_VERSION"
    echo ""
    echo "Please upgrade Java:"
    echo "  • Download from: https://adoptium.net/"
    echo "  • Or use Homebrew: brew install openjdk@17"
    echo ""
    exit 1
fi

echo "✅ Java $JAVA_VERSION detected"

# Check Maven
echo ""
echo "🔍 Step 2: Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "❌ ERROR: Maven is not installed"
    echo ""
    echo "Please install Maven 3.6 or higher:"
    echo "  • Download from: https://maven.apache.org/download.cgi"
    echo "  • Or use Homebrew: brew install maven"
    echo ""
    exit 1
fi

echo "✅ Maven detected"

# Check Google Maps API Key
echo ""
echo "🗺️  Step 3: Checking Google Maps API Key..."
if grep -q "YOUR_GOOGLE_MAPS_API_KEY_HERE" src/main/resources/application.properties; then
    echo "⚠️  WARNING: Google Maps API Key is not configured"
    echo ""
    echo "The application will work, but map features will be limited."
    echo ""
    echo "To enable full map functionality:"
    echo "  1. Visit: https://console.cloud.google.com/"
    echo "  2. Create a new project or select existing"
    echo "  3. Enable 'Maps JavaScript API'"
    echo "  4. Create an API Key"
    echo "  5. Edit: src/main/resources/application.properties"
    echo "  6. Replace: YOUR_GOOGLE_MAPS_API_KEY_HERE with your key"
    echo ""
    read -p "Configure API key now? (y/n) " -n 1 -r
    echo ""
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo ""
        read -p "Enter your Google Maps API Key: " API_KEY
        if [ ! -z "$API_KEY" ]; then
            # Backup original file
            cp src/main/resources/application.properties src/main/resources/application.properties.backup
            # Replace API key
            sed -i '' "s/YOUR_GOOGLE_MAPS_API_KEY_HERE/$API_KEY/g" src/main/resources/application.properties
            echo "✅ API Key configured successfully!"
        else
            echo "⚠️  No key entered. You can configure it later."
        fi
    fi
else
    echo "✅ Google Maps API Key is configured"
fi

# Build project
echo ""
echo "🔨 Step 4: Building the project..."
echo "   This may take a few minutes on first run..."
echo ""

mvn clean install -DskipTests > /tmp/realestate-build.log 2>&1

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Check the log for details:"
    echo ""
    tail -20 /tmp/realestate-build.log
    echo ""
    echo "Full log available at: /tmp/realestate-build.log"
    exit 1
fi

echo "✅ Build successful!"

# Final summary
echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║                                                            ║"
echo "║     ✨ Setup Complete! Ready to launch                    ║"
echo "║                                                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "📚 Quick Start:"
echo ""
echo "  1. Start the application:"
echo "     ./run.sh"
echo ""
echo "  2. Open your browser:"
echo "     http://localhost:8080"
echo ""
echo "  3. Explore the features:"
echo "     • Browse properties"
echo "     • View interactive map"
echo "     • Save favorites"
echo "     • Create alerts"
echo ""
echo "📖 Documentation:"
echo "  • README.md          - Full documentation"
echo "  • QUICKSTART.md      - Quick start guide"
echo "  • API_TESTING.md     - API testing guide"
echo "  • PROJECT_SUMMARY.md - Project overview"
echo ""
echo "🗄️  Database Console:"
echo "  • URL: http://localhost:8080/h2-console"
echo "  • JDBC URL: jdbc:h2:mem:realestate"
echo "  • Username: sa"
echo "  • Password: (leave empty)"
echo ""
echo "🎯 Sample Data:"
echo "  • 8 properties automatically loaded"
echo "  • Various types and price ranges"
echo "  • Located in Bay Area, California"
echo ""
echo "💡 Tips:"
echo "  • Press Ctrl+C to stop the server"
echo "  • Check logs if something goes wrong"
echo "  • Customize in application.properties"
echo ""
echo "Ready to start? Run: ./run.sh"
echo ""
