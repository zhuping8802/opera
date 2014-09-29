package org.ping.study.pattern.decorate;

/**
 * 装饰类，继承被妆饰的类，并实现它方法
 * @author ping
 *
 */
public class DecorateCompote extends Compote {

	private Compote compote;

	public void setCompote(Compote compote) {
		this.compote = compote;
	}

	@Override
	protected void show() {
		// TODO Auto-generated method stub
		if(this.compote != null){
			this.compote.show();
		}
	}
	
	public static void main(String[] args) {
		Compote person = new Person();
		DecorateCompote a = new DecorateA();
		DecorateCompote b = new DecorateB();
		a.setCompote(person);
		b.setCompote(a);
		b.show();
	}
}
