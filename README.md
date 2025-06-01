# BlockChain Java Implementation

A comprehensive blockchain implementation written in Java, featuring a complete cryptocurrency system with mining, transactions, and network functionality.

## 🚀 Features

- **Complete Blockchain Architecture**: Implements a full blockchain with blocks, transactions, and proof-of-work mining
- **Cryptocurrency System**: Digital currency with wallet management and transaction processing
- **Mining Algorithm**: Proof-of-work consensus mechanism with adjustable difficulty
- **Network Simulation**: Multi-user network environment with concurrent mining
- **Cryptographic Security**: RSA encryption for transaction signatures and SHA-256 for block hashing
- **Transaction Validation**: Comprehensive validation system to prevent fraud and double-spending
- **User Management**: Multi-user support with unique key pairs and wallets

## 📁 Project Structure

```
BlockChain_Java/
├── Main.java                 # Entry point and simulation runner
├── blockpack/
│   └── Block.java            # Block structure and implementation
├── transactionspack/
│   ├── Transaction.java      # Transaction class with digital signatures
│   └── TransactionData.java  # Transaction data structure
├── userpack/
│   └── User.java            # User class with wallet and mining functionality
├── cryptographypack/
│   └── CRYPTO.java          # Cryptographic utilities (RSA, SHA-256)
└── net/
    └── Network.java         # Network management and blockchain consensus
```

## 🛠️ Core Components

### Block (`blockpack/Block.java`)

- Contains transaction data, previous block hash, and nonce
- Immutable structure ensuring blockchain integrity
- Support for genesis block creation

### Transaction (`transactionspack/`)

- **Transaction.java**: Digital signatures using RSA encryption
- **TransactionData.java**: Core transaction information (sender, receiver, amount)
- Reference-based system for tracking coin ownership

### User (`userpack/User.java`)

- **Wallet Management**: Tracks coin ownership through transaction references
- **Mining Capability**: Built-in miner with proof-of-work algorithm
- **Transaction Creation**: Send coins to other users with miner rewards
- **Key Management**: RSA key pair generation and management

### Cryptography (`cryptographypack/CRYPTO.java`)

- **RSA Encryption**: For transaction signatures and verification
- **SHA-256 Hashing**: For block hash generation and proof-of-work
- **Key Generation**: Secure key pair creation for users

### Network (`net/Network.java`)

- **Blockchain Management**: Maintains the complete blockchain
- **Transaction Pool**: Manages pending transactions
- **Consensus**: Validates and adds new blocks to the chain
- **Multi-user Support**: Handles multiple concurrent users and miners

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- No external dependencies required (uses only Java standard library)

### Running the Application

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   cd BlockChain_Java
   ```

2. **Compile the project**:

   ```bash
   javac *.java */*.java
   ```

3. **Run the simulation**:
   ```bash
   java Main
   ```

### Example Usage

The `Main.java` file contains a comprehensive simulation that demonstrates:

- Creating multiple users (PEPPE, MARCO, COLA, etc.)
- Mining new blocks
- Performing transactions between users
- Demonstrating fraud detection (BadUser example)
- Network consensus and validation

## 💡 Key Features Demonstrated

### Mining System

- Users can start/stop mining at any time
- Proof-of-work algorithm with adjustable difficulty
- Miners receive rewards for successfully mining blocks
- Concurrent mining support

### Transaction System

- Send coins between users with transaction fees
- Reference-based coin tracking (no double-spending)
- Digital signature verification
- Automatic change calculation

### Security Features

- **Fraud Detection**: Invalid transactions are rejected
- **Digital Signatures**: All transactions are cryptographically signed
- **Chain Validation**: Each block references the previous block's hash
- **Double-spending Prevention**: Reference-based transaction validation

## 🔧 Technical Details

### Mining Algorithm

- Uses SHA-256 proof-of-work
- Difficulty adjustable via leading zeros requirement
- Nonce-based mining with incremental search

### Transaction Validation

1. Verify digital signature
2. Check transaction references
3. Validate sufficient funds
4. Prevent double-spending

### Network Consensus

- First valid block wins
- Invalid transactions are rejected
- Automatic network synchronization

## 🎯 Use Cases

This implementation serves as:

- **Educational Tool**: Learn blockchain and cryptocurrency concepts
- **Research Platform**: Experiment with consensus algorithms
- **Proof of Concept**: Demonstrate blockchain functionality
- **Development Base**: Foundation for more complex blockchain projects

## 🐛 Known Limitations

- Single-threaded validation (could be optimized)
- In-memory storage only (no persistence)
- Simple network simulation (not real P2P networking)
- Fixed block size and transaction limits

## 🤝 Contributing

Feel free to submit issues, feature requests, or pull requests to improve this blockchain implementation.

## 📄 License

This project is open source. Please check the repository for specific license information.

## 👥 Authors

- Fausto Zamparelli

## 🔗 Additional Notes

This implementation focuses on educational value and demonstrates core blockchain concepts including:

- Cryptographic hashing and digital signatures
- Proof-of-work consensus
- Transaction validation and wallet management
- Network simulation and multi-user interaction

For production use, consider additional features like:

- Persistent storage
- Real network protocols
- Advanced consensus mechanisms
- Enhanced security measures
