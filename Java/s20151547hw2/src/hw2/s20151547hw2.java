package hw2;
class Employee{
	protected long id;
	protected String name;
	protected int age;
	public void print() {
		System.out.println("[e00"+id+","+name+","+age+"]");
	}
	public Employee(long id,String name,int age) {
		this.id=id;
		this.name = name;
		this.age = age;
	}
}
class Manager extends Employee{
	protected String department;
	public void print() {
		System.out.println("[m00"+id+","+name+","+age+","+department+"]");
	}
	public Manager(long id,String name,int age,String department) {
		super(id,name,age);
		this.department=department;
	}
}
public class s20151547hw2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee em[] = new Employee[7];
		System.out.println("<<output>>");
		em[0] = new Employee(1,"John",27);
		em[1] = new Employee(2,"Eunjin",25);
		em[2] = new Employee(3,"Alex",26);
		em[3] = new Employee(4,"Jenny",23);
		em[4] = new Employee(5,"Tom",25);
		em[5] = new Manager(1,"Andy",33,"Marketing");
		em[6] = new Manager(2,"Kate",30,"Sales");
		System.out.println("===Employee===");
		for(int i=0;i<5;i++) {
			em[i].print();
		}
		System.out.println("===Manager===");
		for(int i=5;i<7;i++) {
			em[i].print();
		}
	}
}
