package compilers;

/**
 * @author :zhangyi
 * @description:����������
 */
public class Compiler {
    public static void main(String[] args){
        LexAnalyse lex=new LexAnalyse();
        lex.generateWordList();
        if(lex.lexErrorTag){
            System.out.println("�﷨����δͨ��");
            return;
        }
        lex.outputWordsList();


        Parser parser=new Parser(lex);
        parser.grammarAnalyse();
        if(parser.grammarErrorFlag){
            System.out.println("�������δͨ��");
            parser.outputError();
            return;
        }

        parser.outputFourElement();

        System.out.println();
        parser.outputVariables();


    }
}
