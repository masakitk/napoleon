package model;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class FacilitatorTest {

	@Test
	public void T01_��ԑJ��_�J�[�h�z��_�i�|���I�����܂�_�Q�[���J�n_12���E���h����ďI���() {
		Facilitator facilitator = Facilitator.New(getGameContext());
		facilitator.ServeCards();
		assertThat(facilitator.getStatus(), IsEqual.equalTo(Status.CardServed));
		facilitator.determineNapoleon();
		assertThat(facilitator.getContext().napoleonDetermined(), IsEqual.equalTo(true));
		assertThat(facilitator.getStatus(), IsEqual.equalTo(Status.GameStarted));
		List<Integer> rounds = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
		for (Integer i : rounds) {
			facilitator.StartRounds();
			assertThat(facilitator.getCurrentRoundNo(), IsEqual.equalTo(i));
		}
		assertThat(facilitator.getStatus(), IsEqual.equalTo(Status.GameEnded));
	}

	@Test
	public void T02_��ԑJ��_�J�[�h�z��_�i�|���I�����܂�Ȃ�_�I��() {
		Facilitator facilitator = Facilitator.New(getGameContext());
		facilitator.ServeCards();
		assertThat(facilitator.getStatus(), IsEqual.equalTo(Status.CardServed));
		facilitator.determineNapoleon();
		assertThat(facilitator.getContext().napoleonDetermined(), IsEqual.equalTo(false));
		assertThat(facilitator.getStatus(), IsEqual.equalTo(Status.GameEnded));
	}

	
	private GameContext getGameContext() {
		return new GameContext(Table.New(), new Player[]{new Player(), new Player(), new Player(), new Player()});
	}
	@Test
	public void T02_1�����Ă�����錾���Ȃ�������ēx�z�蒼��() {
//		Facilitator facilitator = new Facilitator();
//		
//		facilitator.setContext(new GameContext(new Table(), new Player[4]));
//		Player candidate = facilitator.askForAll();
//		assertThat(candidate, IsNull.nullValue());
//		
//		assertThat(candidate, IsNull.nullValue());
		
	}

}
