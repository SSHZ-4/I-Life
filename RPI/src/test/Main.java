package test;

import zx.rpc.support.RPC;
import zx.rpc.support.Server;


public class Main {
	public static void main(String[] args) {

		Server server = new RPC.RPCServer();
		server.register(Media.class, MeidaImpl.class);
		server.start();
	
	}

}
