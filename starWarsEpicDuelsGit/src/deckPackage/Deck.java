package deckPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public abstract class Deck implements Serializable {

	private static final long serialVersionUID = -7512555506367560757L;
	protected Stack<Cards> deck;
	private ArrayList<Cards> graveYard;
	private String name;

	public Deck(String name) {
		this.name = name;
		deck = new Stack<Cards>();
		graveYard = new ArrayList<Cards>();
		addNormalCards();
		addSpecialCards();
		for (int i = 0; i < (Math.random() * 1336) + 1; i++) {
			shuffle();
		}
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	public Cards draw() {
		if (deck.isEmpty()) {
			repopulate();
		}
		Cards card = deck.pop();
		return card;
	}

	public void graveYard(Cards card) {
		graveYard.add(card);
	}

	public boolean isEmpty() {
		return deck.isEmpty();
	}

	public void repopulate() {
		while (!graveYard.isEmpty()) {
			deck.push(graveYard.get(0));
			graveYard.remove(0);
		}
		shuffle();
	}

	public String getName() {
		return name;
	}

	protected abstract void addSpecialCards();

	protected abstract void addNormalCards();

	public int size() {
		return deck.size();
	}

	public void push(Cards card) {
		deck.push(card);
	}

	public ArrayList<Cards> getGraveYard() {
		return graveYard;
	}

	public int getGraveSize() {
		return graveYard.size();
	}

	public Cards getCardInGrave(int index) {
		return graveYard.get(index);
	}

}
