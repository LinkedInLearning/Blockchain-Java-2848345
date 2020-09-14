package dev.wittek.blockchain;

import org.web3j.crypto.Credentials;
import org.web3j.model.Greeter;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class Web3jExampleSmartContract {

    public static void main(String[] args) throws Exception {
        // 127.0.0.1:7545 is the default for a local Ganache network
        var web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));

        // TODO: You have to add a private key (e.g. from Ganache) of the account used to deployment
        var creds = Credentials.create("PRIVATE_KEY");

        var gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));

        // can be used to load an already deployed contract
//        var greeter = Greeter.load("0x4b2c56717ac02d5fbd1be5a56f621cfdcfaf8424", web3, creds, gasProvider);
        var greeter = Greeter.deploy(web3, creds, gasProvider).send();

        System.out.println(greeter.getContractAddress());

        System.out.println(greeter.greet().send());

        greeter.store("Java rocks!").send();

        System.out.println(greeter.greet().send());

    }

}
