package chaturaji.game;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import chaturaji.communication.Protocol;

@Entity
public class Player implements Serializable {

	transient
	private Protocol comm;
	
	@GeneratedValue
	@Id
	private Long id;
	
	@Column(length=15)
	//@Id
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private GameServer server;
	
	@Column(nullable=false)
	private Color color;
	
	@Column(nullable=false)
	private Integer points;
	
	@Column(nullable=false)
	private Integer nb_kings;
	
	public Player(GameServer server, Protocol comm, Color color) {
		this.server = server;
		this.comm = comm;
		this.color = color;
		points = 0;
		nb_kings = 0;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void send(JSONObject message) {
		comm.send(message);
	}
	
	public Protocol getProtocol() {
		return comm;
	}
	
	public GameServer attachedServer() {
		return server;
	}
	
	public void addPoints(Integer points) {
		if (points == Type.KING.getType()) {
			nb_kings++;
		}
		this.points += points;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public Integer getNbKings() {
		return nb_kings;
	}
	
}
