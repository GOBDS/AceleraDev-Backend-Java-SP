package challenge;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	private QuoteRepository repository;

	@Override
	public Quote getQuote() {
		Integer count = (int)repository.count();
		return repository.findById(randomIndex(count)).get();
	}

	@Override
	public Quote getQuoteByActor(String actor) {
		List<Quote> quotes = repository.findByActor(actor);
		return quotes.get(randomIndex(quotes.size()));
	}

	private Integer randomIndex(Integer limit) {
		Random rand = new Random();
		Integer index = rand.nextInt(limit);
		return index;
	}

}
