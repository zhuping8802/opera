package org.ping.pattern.strategy;

/**
 * 策略模式具体使用方，保有策略抽象接口，具体什么策略还是交由客户决定，也可结合其它模式，比如工厂模式
 * @author ping
 *
 */
public class Context {

	private Strategy strategy;

	public Context(Strategy strategy) {
		super();
		this.strategy = strategy;
	}
	
	
	public int getResult(){
		return strategy.strategyResult();
	}
	
	public static void main(String[] args) {
		Strategy strategy = new StratgeyAdd();
		Context context = new Context(strategy);
		System.out.println(context.getResult());
		
		context = new Context(new StrategyDif());
		System.out.println(context.getResult());
	}
}
