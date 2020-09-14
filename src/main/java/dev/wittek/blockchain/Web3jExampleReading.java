package dev.wittek.blockchain;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

public class Web3jExampleReading {

    public static void main(String[] args) throws IOException {

        var web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));

        var blockNumber = web3.ethBlockNumber().send().getBlockNumber();
        System.out.println(blockNumber);

        var sum = IntStream.range(0, blockNumber.intValue() + 1)
                .mapToObj(i -> {
                    try {
                        var block = web3.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), false)
                                .send();
                        return Optional.of(block);
                    } catch (IOException e) {
                        return Optional.<EthBlock>empty();
                    }
                })
                .flatMap(Optional::stream)
                .mapToInt(block -> block.getBlock().getTransactions().size())
                .sum();

        System.out.println(sum);


    }

}
