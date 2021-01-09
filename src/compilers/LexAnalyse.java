package compilers;

import utils.CustomizeError;
import utils.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhangyi
 * @description:�ʷ�������
 */
public class LexAnalyse {
    List<Word> wordList=new ArrayList<>();
    List<CustomizeError> customizeErrorList =new ArrayList<>();

    int wordCount=0;
    int errorCount=0;
    boolean noteTag=false;
    public boolean lexErrorTag=false;

    public LexAnalyse(){

    }

    //��ȡ�ļ�
    public List<String> readFile() {
        try {
            BufferedReader reader=new BufferedReader(new FileReader("test-files\\test.c"));
            List<String> ans=new ArrayList<>();
            String temp=reader.readLine();
            while (temp!=null){
                System.out.println(temp);
                ans.add(temp);
                temp=reader.readLine();
            }
            return ans;
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static boolean isID(String word) {
        boolean flag = false;
        int i = 0;
        if (Word.isKey(word))
            return flag;
        char temp = word.charAt(i);
        if (Character.isLetter(temp) || temp == '_') {
            for (i = 1; i < word.length(); i++) {
                temp = word.charAt(i);
                if (Character.isLetter(temp) || temp == '_' || Character.isDigit(temp))
                    continue;
                else
                    break;
            }
            if (i >= word.length())
                flag = true;
        } else
            return flag;

        return flag;
    }

    //���ж�ȡ���룬���ɵ���
    //�����ڶ���ע�ͷ���ʱ����true
    public boolean analyse(String str, int line){
        int index=0;
        int length=str.length();
        while (index<length){
            char ch=str.charAt(index);
            //�ǿ��ַ�ʱ����
            if(ch==' '||ch=='\t'||ch=='\r'){
                index++;
                continue;
            }
            //���ֵ���ע��
            if(ch=='/'&&index+1<length&&str.charAt(index+1)=='/'){
                return false;
            }
            if(ch=='/'&&index+1<length&&str.charAt(index+1)=='*')
                return true;
            Word word;
            boolean tag=false;
            //�����
            switch (ch){
                case '+':
                    tag=true;
                    if(index+1<length&&str.charAt(index+1)=='+'){
                        wordList.add(new Word(++wordCount,"=",Word.OPERATOR,line));
                        wordList.add(new Word(++wordCount,wordList.get(wordCount-3).value,Word.IDENTIFIER,line));
                        wordList.add(new Word(++wordCount,"+",Word.OPERATOR,line));
                        wordList.add(new Word(++wordCount,"1",Word.INT_CONST,line));
                        index=index+2;
                    }else {
                        wordList.add(new Word(++wordCount,str.substring(index,index+1),Word.OPERATOR,line));
                        index++;
                    }
                    break;
                case '-':
                    tag=true;
                    if(index+1<length&&str.charAt(index+1)=='-'){
                        wordList.add(new Word(++wordCount,"=",Word.OPERATOR,line));
                        wordList.add(new Word(++wordCount,wordList.get(wordCount-3).value,Word.IDENTIFIER,line));
                        wordList.add(new Word(++wordCount,"-",Word.OPERATOR,line));
                        wordList.add(new Word(++wordCount,"1",Word.INT_CONST,line));
                        index=index+2;
                    }else {
                        wordCount++;
                        word=new Word(wordCount,str.substring(index,index+1),Word.OPERATOR,line);
                        index++;
                        wordList.add(word);
                    }
                    break;
                case '*':
                case '/':
                    tag=true;
                    wordCount++;
                    word=new Word(wordCount,str.substring(index,index+1),Word.OPERATOR,line);
                    this.wordList.add(word);
                    index++;
                    break;
                case '&':
                    tag=true;
                    wordCount++;
                    if(index+1<length&&str.charAt(index+1)=='&'){
                        word=new Word(wordCount,str.substring(index,index+2),Word.OPERATOR,line);
                        index=index+2;
                    }else{
                        word=new Word(wordCount,str.substring(index,index+1),Word.OPERATOR,line);
                        index++;
                    }
                    wordList.add(word);
                    break;
                case '|':
                    tag=true;
                    wordCount++;
                    if(index+1<length&&str.charAt(index+1)=='|'){
                        word=new Word(wordCount,str.substring(index,index+2),Word.OPERATOR,line);
                        index=index+2;
                    }else{
                        word=new Word(wordCount,str.substring(index,index+1),Word.OPERATOR,line);
                        index++;
                    }
                    wordList.add(word);
                    break;
                case '=':
                case '<':
                case '>':
                case '!':
                    tag=true;
                    wordCount++;
                    if(index+1<length&&str.charAt(index+1)=='='){
                        word=new Word(wordCount,str.substring(index,index+2),Word.OPERATOR,line);
                        index=index+2;
                    }else{
                        word=new Word(wordCount,str.substring(index,index+1),Word.OPERATOR,line);
                        index++;
                    }
                    wordList.add(word);
                    break;
            }
            if(tag)
                continue;
            //����
            if(Character.isDigit(ch)){
                int indexTemp=index;
                while (indexTemp<length&&(Character.isDigit(str.charAt(indexTemp)))){
                    indexTemp++;
                }
                if(indexTemp==length||str.charAt(indexTemp)!='.'){//�����Ϊ��������
                    wordCount++;
                    word=new Word(wordCount,str.substring(index,indexTemp),Word.INT_CONST,line);
                }
                else{//�����Ϊ������
                    indexTemp++;
                    while (indexTemp<length&&(Character.isDigit(str.charAt(indexTemp)))){
                        indexTemp++;
                    }
                    wordCount++;
                    word=new Word(wordCount,str.substring(index,indexTemp),Word.FLOAT_CONST,line);
                }
                index=indexTemp;
                wordList.add(word);
            }
            //��ĸ
            else if(Character.isLetter(ch)){
                int indexTemp = index;
                indexTemp++;
                while ((indexTemp < length)
                        && (!Word.isBoundarySign(String.valueOf(str.charAt(indexTemp))))
                        && (!Word.isOperator(String.valueOf(str.charAt(indexTemp))))
                        && (str.charAt(indexTemp) != ' ')
                        && (str.charAt(indexTemp) != '\t')
                        && (str.charAt(indexTemp) != '\r')
                        && (str.charAt(indexTemp) != '\n')) {
                    indexTemp++;
                }
                wordCount++;
                String temp=str.substring(index,indexTemp);
                if(temp.equals("true")){
                    word=new Word(wordCount,"true",Word.BOOL_CONST,line);
                }else if(temp.equals("false")){
                    word=new Word(wordCount,"false",Word.BOOL_CONST,line);
                }else {
                    word = new Word(wordCount, str.substring(index, indexTemp), 0, line);
                    if (Word.isKey(word.value)) {
                        word.type = Word.KEY;
                    } else if (isID(word.value)) {
                        word.type = Word.IDENTIFIER;
                    } else {
                        word.type = Word.UNIDEF;
                        word.flag = false;
                        errorCount++;
                        customizeErrorList.add(new CustomizeError(errorCount, "�Ƿ���ʶ��", word.line, word));
                        lexErrorTag = true;
                    }
                }
                index = indexTemp;
                wordList.add(word);
            }
            // ���Ǳ�ʶ�������ֳ������ַ�������
            else {
                ch=str.charAt(index);
                switch (ch) {
                    case '[':
                    case ']':
                    case '(':
                    case ')':
                    case '{':
                    case '}':
                    case ',':
                    case '"':
                    case '.':
                    case ';':
                    case '%':
                    case '>':
                    case '<':
                    case '?':
                    case '#':
                        wordCount++;
                        word = new Word(wordCount,String.valueOf(ch),0,line);
                        if (Word.isOperator(word.value))
                            word.type = Word.OPERATOR;
                        else if (Word.isBoundarySign(word.value))
                            word.type = Word.BOUNDARYSIGN;
                        else
                            word.type = Word.END;
                        break;
                    default:
                        wordCount++;
                        word = new Word(wordCount,String.valueOf(ch),Word.UNIDEF,line);
                        word.flag = false;
                        errorCount++;
                        customizeErrorList.add(new CustomizeError(errorCount, "�Ƿ���ʶ��", word.line, word));
                        lexErrorTag = true;
                }
                index++;
                wordList.add(word);
            }
        }
        return false;
    }

    //�ʷ�����
    public void analyseWords(List<String> strings){
        int i=0;
        for(;i<strings.size();i++){
            if(analyse(strings.get(i),i+1)){
                i++;
                //ɾ������ע��
                String temp=deleteMultiLineComment(strings.get(i));
                while (temp==null){
                    i++;
                    temp=deleteMultiLineComment(strings.get(i));
                }
                analyse(temp,i+1);
            }
        }

        //���ӽ�������
        if (wordList.get(wordList.size() - 1).type!=Word.END) {
            wordCount++;
            wordList.add(new Word(wordCount, "#", Word.END, i+2));
        }
    }

    //ɾ������ע�ͷ���
    public String deleteMultiLineComment(String str){
        int length=str.length();
        for(int i=0;i<length-1;i++) {
            if(str.charAt(i)=='*'&&str.charAt(i+1)=='/'){
                return str.substring(i+2);
            }
        }
        return null;
    }

    //���뵥���б�
    public void generateWordList(){
        List<String> strings=this.readFile();
        this.analyseWords(strings);
    }

    //��������б�
    public void outputWordsList(){
        for(Word word:this.wordList){
            System.out.println(word.toString());
        }
    }


    public static void main(String[] args){
        LexAnalyse lex=new LexAnalyse();
        List<String> strings=lex.readFile();
        lex.analyseWords(strings);
        for(Word word:lex.wordList){
            System.out.println(word.toString());
        }

        for(CustomizeError customizeError :lex.customizeErrorList){
            System.out.println(customizeError.toString());
        }
    }
}
