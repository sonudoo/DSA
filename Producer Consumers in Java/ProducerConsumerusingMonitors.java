class Resource{
	private int arr[]=new int[10];
	int full=0;
	public void produce(int val){
		synchronized(this){
			if(full==10){
				try{wait();}catch(InterruptedException e){}
			}
			arr[full++] = val;
			System.out.println("Produced: "+val);
			try{Thread.sleep(1000);}catch(InterruptedException e){}
			notify();
		}
	}
	public void consume(){
		synchronized(this){
			if(full==0){
				try{wait();}catch(InterruptedException e){}
			}
			full--;
			System.out.println("Consumed: "+arr[full]);
			try{Thread.sleep(1000);}catch(InterruptedException e){}
			notify();
		}
	}
}
class Producer extends Thread{
	Resource r;
	Producer(Resource r){
		this.r = r;
	}
	public void run(){
		while(true){
			int x = (int)(Math.random()*10000);
			r.produce(x);
		}
	}
}
class Consumer extends Thread{
	Resource r;
	Consumer(Resource r){
		this.r = r;
	}
	public void run(){
		while(true){
			r.consume();
		}
	}
}
class he{
	public static void main(String args[]){
		Resource r = new Resource();
		Producer p = new Producer(r);
		Consumer c = new Consumer(r);
		p.setPriority(10);
		c.setPriority(1);
		p.start();
		c.start();
	}
}