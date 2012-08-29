package model;

import java.util.List;

public class Turn {
	private List<Card> cards;
	private int no;
	
	private Turn(List<Card> cards, int no) {
		super();
		this.cards = cards;
		this.no = no;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public int getNo() {
		return no;
	}

	public static Turn New(List<Card> list, int no) {
		if(no < 1 || 12 < no) throw new IllegalArgumentException("�^�[���ԍ���1�ȏ�12�ȉ��ł���K�v������܂�");
		
		return new Turn(list, no);
	}

	public Marks getLeadMark() {
		return cards.size() == 0 ? null : cards.get(0).getMark();
	}
	
}
