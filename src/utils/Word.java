package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhangyi
 * @description:��
 */
public class Word {
    public final static int KEY = 1;//�ؼ���
    public final static int OPERATOR = 2;//������
    public final static int INT_CONST = 3;//���γ���
    public final static int CHAR_CONST = 4;//�ַ�����
    public final static int BOOL_CONST =5; //��������
    public final static int IDENTIFIER = 6;//��ʶ��
    public final static int BOUNDARYSIGN =7; //���;
    public final static int END =8; //������
    public final static int UNIDEF =9;// δ֪����
    public final static int FLOAT_CONST=10;//�����ͳ���;
    public static List<String> key = new ArrayList<>();// �ؼ��ּ���
    public static List<String> boundarySign = new ArrayList<>();// �������
    public static List<String> operator = new ArrayList<>();// ���������

    public int id;// �������
    public String value;// ���ʵ�ֵ
    public int type;// ��������
    public int line;// ����������
    public boolean flag = true;//�����Ƿ�Ϸ�

    public Word() {

    }

    public Word(int id, String value, int type, int line) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.line = line;
    }
    public static boolean isKey(String word) {
        return key.contains(word);
    }

    public static boolean isOperator(String word) {
        return operator.contains(word);
    }

    public static boolean isBoundarySign(String word) {
        return boundarySign.contains(word);
    }

    public String toString(){
        return this.id+"\t"+this.value+"\t"+this.type+"\t"+this.line+"\t"+this.flag;
    }

    static {
        Word.operator.add("+");
        Word.operator.add("-");
        Word.operator.add("++");
        Word.operator.add("--");
        Word.operator.add("*");
        Word.operator.add("/");
        Word.operator.add(">");
        Word.operator.add("<");
        Word.operator.add(">=");
        Word.operator.add("<=");
        Word.operator.add("==");
        Word.operator.add("!=");
        Word.operator.add("=");
        Word.operator.add("&&");
        Word.operator.add("||");
        Word.operator.add("!");
        Word.operator.add(".");
        Word.operator.add("?");
        Word.operator.add("|");
        Word.operator.add("&");
        Word.boundarySign.add("(");
        Word.boundarySign.add(")");
        Word.boundarySign.add("{");
        Word.boundarySign.add("}");
        Word.boundarySign.add(";");
        Word.boundarySign.add(",");
        
        Word.key.add("void");
        Word.key.add("main");
        Word.key.add("int");
        Word.key.add("char");
        Word.key.add("float");
        Word.key.add("if");
        Word.key.add("else");
        Word.key.add("while");
        Word.key.add("for");
        Word.key.add("do");
    }
}
