package dev.wittek.blockchain;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.LinkedList;
import java.util.List;

public class ProofOfWorkExample {

    public static void main(String[] args) {

        var blockchain = new MyBlockchain();

        blockchain.mineTransactionIntoBlock("foo");
        blockchain.mineTransactionIntoBlock("bar");
        blockchain.mineTransactionIntoBlock("baz");

        System.out.println(blockchain);

    }

    static class MyBlockchain {

        private final List<Block> blocks = new LinkedList<>();

        private static final String DIFFICULTY = "0000";

        public MyBlockchain() {
            blocks.add(new Block("genesis", "0"));
        }

        public void mineTransactionIntoBlock(String payload) {
            var b = new Block(payload, blocks.get(blocks.size() - 1).calculateHash());
            b.mine(DIFFICULTY);
            blocks.add(b);
        }

        @Override
        public String toString() {
            return "MyBlockchain{" +
                    "blocks=" + blocks +
                    '}';
        }
    }

    static class Block {
        private final String data;
        private final String previousHash;
        private long nonce;

        public Block(String data, String previousHash) {

            this.data = data;
            this.previousHash = previousHash;
        }

        public String calculateHash() {
            return DigestUtils.sha256Hex(previousHash + data + nonce);
        }

        @Override
        public String toString() {
            return "Block{" +
                    "data='" + data + '\'' +
                    ", previousHash='" + previousHash + '\'' +
                    ", nonce=" + nonce +
                    '}';
        }

        public void mine(String difficulty) {

            while (!calculateHash().startsWith(difficulty)) {
                nonce++;
            }

        }
    }
}
