package utils;

/**
 * @author :zhangyi
 * @description:����
 */
public class CustomizeError {
    int id;//������ţ�
    String info;//������Ϣ��
    int line;//����������
    Word word;//����ĵ���

    public CustomizeError() {
    }

    public CustomizeError(int id, String info, int line, Word word) {
        this.id = id;
        this.info = info;
        this.line = line;
        this.word = word;
    }
    public String toString(){
        return this.id+"\t"+this.info+"\t"+this.line+"\t"+this.word.value;
    }

}
