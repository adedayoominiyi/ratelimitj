package es.moki.aerospike.extensions;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.CommitLevel;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.ReadModeAP;
import com.aerospike.client.policy.Replica;
import com.aerospike.client.policy.WritePolicy;
import java.util.concurrent.Executors;

public class AerospikeClientFactory {

  public static AerospikeClient getAerospikeClient() {

    Policy readPolicy = new Policy();
    readPolicy.maxRetries = 1;
    readPolicy.readModeAP = ReadModeAP.ONE;
    readPolicy.replica = Replica.MASTER_PROLES;
    readPolicy.sleepBetweenRetries = 5;
    readPolicy.totalTimeout = 1000;
    readPolicy.sendKey = true;

    WritePolicy writePolicy = new WritePolicy();
    writePolicy.maxRetries = 2;
    writePolicy.readModeAP = ReadModeAP.ALL;
    writePolicy.replica = Replica.MASTER_PROLES;
    writePolicy.sleepBetweenRetries = 5;
    writePolicy.commitLevel = CommitLevel.COMMIT_ALL;
    writePolicy.totalTimeout = 10000;
    writePolicy.sendKey = true;

    ClientPolicy clientPolicy = new ClientPolicy();
    clientPolicy.maxConnsPerNode = 1;
    clientPolicy.readPolicyDefault = readPolicy;
    clientPolicy.writePolicyDefault = writePolicy;
    clientPolicy.failIfNotConnected = true;
    clientPolicy.timeout = 30000;
    clientPolicy.threadPool = Executors.newFixedThreadPool(1);

    AerospikeClient aerospikeClient = new AerospikeClient(clientPolicy,
        new Host("127.0.0.1", 3000));
    return aerospikeClient;
  }
}
