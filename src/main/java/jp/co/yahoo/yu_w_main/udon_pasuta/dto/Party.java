/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.dto;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Ally;

import java.util.Objects;

public final class Party<L extends Character> {
	private final SimpleObjectProperty<L> leader;
	private final ObservableList<Ally> members;

	public Party(L leader, ObservableList<Ally> members) {
		this.leader = new SimpleObjectProperty<>(this, "leader", leader);
		this.members = members;
	}

	public L getLeader() {
		return leader.get();
	}

	public ObjectProperty<L> leaderProperty() {
		return leader;
	}

	public ObservableList<Ally> members() {
		return members;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Party) obj;
		return Objects.equals(this.leader, that.leader) &&
			   Objects.equals(this.members, that.members);
	}

	@Override
	public int hashCode() {
		return Objects.hash(leader, members);
	}

	@Override
	public String toString() {
		return "Party[" +
			   "main=" + leader + ", " +
			   "members=" + members + ']';
	}


}
