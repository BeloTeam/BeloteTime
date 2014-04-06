package fr.belotime.noyau.control;

import java.util.EventListener;

public interface DonneurListener extends EventListener {

	void donneurChangee(DonneurEvent evt);
}
