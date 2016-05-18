package zx.rpc.support;

import zx.rpc.protocal.Invocation;
/*
 * server接口，有如下方法，具体实现是在RPC的内部类RPCServer中
 * 
 */
public interface Server {
	public void stop();
	public void start();
	public void register(Class interfaceDefiner,Class impl);
	public void call(Invocation invo);
	public boolean isRunning();
	public int getPort();
}
