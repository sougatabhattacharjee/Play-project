package models;
/**
 * @author Sougata
 * Aug 22, 2015 
 */
public class Quora {

	//vars that make quora answered questions from quora_answer
	public String question_answer;
	public String answer;

	//vars that make quora asked questions from quora_question
	public String question_asked;

	public Quora() {
		super();
	}

	public Quora(String question_answer, String answer){
		this.question_answer 	= question_answer;
		this.answer 	= answer;

	}

	public Quora(String question_asked){
		this.question_asked 	= question_asked;
	}
}
