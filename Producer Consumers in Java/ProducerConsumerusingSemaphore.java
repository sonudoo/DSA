class Resource{
	private int arr[]=new int[10];
	int empty=10;
	int full=0;
	boolean mutex=true;
	public void produce(int val){
		if(empty==0 || full==10){
			System.out.println("Buffer is full. Producer waiting..");
			while(empty==0 || full==10){
				try{Thread.sleep(20);}catch(Exception e){}
			}
		}
		if(mutex==false){
			System.out.println("Producer waiting for mutex..");
			while(mutex==false){
				try{Thread.sleep(20);}catch(Exception e){}
			}
		}
		mutex=false;
		arr[full] = val;
		try{Thread.sleep(0);}catch(InterruptedException e){}
		System.out.println("Produced "+val);
		mutex=true;
		empty--;
		full++;
		try{Thread.sleep(20);}catch(InterruptedException e){}
	}
	public void consume(){
		int retval;
		if(empty==10 || full==0){
			System.out.println("Buffer is empty. Consumer waiting..");
			while(empty==10 || full==0){
				try{Thread.sleep(20);}catch(Exception e){}
			}
		}
		if(mutex==false){
			System.out.println("Consumer waiting for mutex..");
			while(mutex==false){
				try{Thread.sleep(20);}catch(Exception e){}
			}
		}
		mutex=false;
		retval = arr[0];
		try{Thread.sleep(5000);}catch(InterruptedException e){}
		System.out.println("Consumed "+retval);
		mutex=true;
		empty++;
		full--;
		try{Thread.sleep(20);}catch(InterruptedException e){}
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