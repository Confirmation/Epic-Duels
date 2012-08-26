package Model;

import java.util.ArrayList;

public class AISetup {

	private Game g;
	private int numAI;
	private int numPlayers;
	private Thread setup;

	public AISetup(Game g, int numAI, int numPlayers) {
		this.g = g;
		this.numAI = numAI;
		this.numPlayers = numPlayers;
		setup = new Thread(new AISetupThread());
		setup.start();
	}

	private class AISetupThread implements Runnable {

		@Override
		public void run() {
			// System.out.println("Waiting for non-AI players");
			while (g.getNumConnectedPlayers() < numPlayers) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// System.out.println("Creating AI players");
			for (int i = 0; i < numAI; i++) {
				ArrayList<ArrayList<Player>> teams = g.getTeams();
				int numTeams = teams.size();
				int playersPerTeam = (numPlayers + numAI) / numTeams;
				int team = 0;
				for (int j = 0; j < numTeams; j++) {
					if (teams.get(j).size() < playersPerTeam) {
						team = j;
						break;
					}
				}
				Client AI = new Client("AI-" + i, "localhost", true);
				// System.out.println("AI-" + i + " is joining team " + team);
				AI.AIChooseCharacterAndTeam(team);
				while (AI.AIIsChoosing()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// System.out.println("Next");
			}
		}
	}

}
