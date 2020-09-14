package dev.wittek.blockchain;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;

public class Web3jExampleWallets {

    public static void main(String[] args) throws Exception {

        var web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        var fileName = WalletUtils.generateNewWalletFile("secret", new File("."));

        var creds = WalletUtils.loadCredentials("secret", new File(fileName));

        // TODO: You have to add a private key (e.g. from Ganache) of the account used to deployment
        var importedCreds = Credentials.create("PRIVATE_KEY");

        var receipt = Transfer.sendFunds(web3, importedCreds, creds.getAddress(), BigDecimal.TEN, Convert.Unit.ETHER).send();
        System.out.println(receipt.getBlockNumber());

        var balance = web3.ethGetBalance(creds.getAddress(), DefaultBlockParameterName.LATEST).send();
        System.out.println(balance.getBalance());

    }

}
