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
	
	//vars that make quora blogs from quora_blog
		public String blog_name;
		/**
		 * @param blog_name the blog_name to set
		 */
		public void setBlog_name(String blog_name) {
			this.blog_name = blog_name;
		}

		/**
		 * @param blog_link the blog_link to set
		 */
		public void setBlog_link(String blog_link) {
			this.blog_link = blog_link;
		}

		public String blog_link;
		
		public String post_heading;
		public String post_link;
		
		
		

	/**
		 * @param post_heading the post_heading to set
		 */
		public void setPost_heading(String post_heading) {
			this.post_heading = post_heading;
		}

		/**
		 * @param post_link the post_link to set
		 */
		public void setPost_link(String post_link) {
			this.post_link = post_link;
		}

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
