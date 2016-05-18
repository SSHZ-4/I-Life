package zx.rpc.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import zx.rpc.protocal.Invocation;


public class RPC {
	public static <T> T getProxy(final Class<T> clazz,String host,int port) {
		
		final Client client = new Client(host,port);
		InvocationHandler handler = new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Invocation invo = new Invocation();
				invo.setInterfaces(clazz);
				invo.setMethod(new zx.rpc.protocal.Method(method.getName(),method.getParameterTypes()));
				invo.setParams(args);
				client.invoke(invo);
				return invo.getResult();
			}
		};
		T t = (T) Proxy.newProxyInstance(RPC.class.getClassLoader(), new Class[] {clazz}, handler);
		return t;
	}
	
	
	public static class RPCServer implements Server{
		
		/*
		 * server的实现类
		 * 实现了如下方法
		 * 	
			public void start();//开启一个listener线程，打开服务器端口，不断地执行   接收请求---回调 过程，直到服务器的isRunning为false。
			public void register(Class interfaceDefiner,Class impl);	//注册对象及其 实现类。 并且new一个对象放入一个hashmap中，以便调用时使用。
			public void call(Invocation invo);//在listenner中进行调用，其中Invocation是由客户端传过来的对象流。在listener中执行。执行过程如下
				首先在serviceEngine中拿到实体对象。
			
			public boolean isRunning();
			public int getPort();
			public void stop();把running设置为false
		 */
		private int port = 20382;
		private Listener listener; 
		private boolean isRuning = true;
		private Map<String ,Object> serviceEngine = new HashMap<String, Object>();
		/**
		 * @param isRuning the isRuning to set
		 */
		public void setRuning(boolean isRuning) {
			this.isRuning = isRuning;
		}

		/**
		 * @return the port
		 */
		public int getPort() {
			return port;
		}

		/**
		 * @param port the port to set
		 */
		public void setPort(int port) {
			this.port = port;
		}

		
		
		
		@Override
		public void call(Invocation invo) {
			System.out.println(invo.getClass().getName());
			Object obj = serviceEngine.get(invo.getInterfaces().getName());
			if(obj!=null) {
				try {
					Method m = obj.getClass().getMethod(invo.getMethod().getMethodName(), invo.getMethod().getParams());
					Object result = m.invoke(obj, invo.getParams());
					invo.setResult(result);
				} catch (Throwable th) {
					th.printStackTrace();
				}
			} else {
				throw new IllegalArgumentException("has no these class");
			}
		}

		@Override
		public void register(Class interfaceDefiner, Class impl) {
			try {
				this.serviceEngine.put(interfaceDefiner.getName(), impl.newInstance());
				System.out.println(serviceEngine);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		@Override
		public void start() {
			System.out.println("启动服务器");
			listener = new Listener(this);
			this.isRuning = true;
			listener.start();
		}

		@Override
		public void stop() {
			this.setRuning(false);
		}

		@Override
		public boolean isRunning() {
			return isRuning;
		}
		
	}
}
	



	
	
	

