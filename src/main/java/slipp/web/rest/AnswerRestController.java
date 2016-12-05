package slipp.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import slipp.model.*;
import slipp.utils.HttpSessionUtils;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerRestController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public ResponseEntity<Answer> create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		return new ResponseEntity<>(answerRepository.save(answer), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerRepository.findOne(id);
		answer.delete(loginUser);
		answerRepository.save(answer);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
