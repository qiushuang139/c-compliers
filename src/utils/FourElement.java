package utils;

/**
 * @author :zhangyi
 * @description:��Ԫʽ
 */
public class FourElement {
    public int id;//��Ԫʽ���
    public String op;//������
    public String arg1;//��һ��������
    public String arg2;//�ڶ���������
    public Object result;//���

    public FourElement(){

    }

    public FourElement(int id,String op,String arg1,String arg2,Object result){
        this.id=id;
        this.op=op;
        this.arg1=arg1;
        this.arg2=arg2;
        this.result=result;
    }

    public String toString(){
        return "("+this.op+","+this.arg1+","+this.arg2+","+this.result+")";
    }
}
