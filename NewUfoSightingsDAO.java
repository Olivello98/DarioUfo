package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<State> loadAllStates() {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public boolean isNeighbor(State s, State ss) {
		String sql = "SELECT COUNT(*) AS cnt " + 
				"FROM neighbor n " + 
				"WHERE n.state1= ? AND n.state2= ?";
		int result = 0;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s.getId());
			st.setString(2, ss.getId());
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				result=rs.getInt("cnt");
			}

			conn.close();
			if(result==0)
				return false;
			else
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public double getPeso(State s, State ss, int anno, int giorni) {
		String sql = "SELECT COUNT(*) AS cnt " + 
				"FROM sighting s,sighting ss " + 
				"WHERE s.state= ? AND ss.state= ? AND YEAR(s.DATETIME)=  ? " + 
				"AND YEAR(ss.DATETIME)= ? AND DATEDIFF(s.DATETIME,ss.DATETIME)<= ? AND s.id>ss.id ";
		double result = 0;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s.getId());
			st.setString(2, ss.getId());
			st.setInt(3, anno);
			st.setInt(4, anno);
			st.setInt(5, giorni);
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				result=(double)rs.getInt("cnt");
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Sighting> getSighting(int anno, int giorni,State s,State ss) {
		String sql = "SELECT * " + 
				"FROM sighting s,sighting ss " + 
				"WHERE s.state= ? AND ss.state= ? AND YEAR(s.DATETIME)=  ? " + 
				"AND YEAR(ss.DATETIME)= ? AND DATEDIFF(s.DATETIME,ss.DATETIME)<= ? AND s.id>ss.id ";
		List<Sighting> list = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s.getId());
			st.setString(2, ss.getId());
			st.setInt(3, anno);
			st.setInt(4, anno);
			st.setInt(5, giorni);
			ResultSet res = st.executeQuery();

			while(res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
