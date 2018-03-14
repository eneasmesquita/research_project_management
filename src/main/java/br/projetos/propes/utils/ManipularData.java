package br.projetos.propes.utils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class ManipularData implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final long MSEC_PER_HOUR = 0x36ee80L;
	public static final long MSEC_PER_DAY = 0x5265c00L;
	private Locale localidade;
	private Calendar calendario;
	private Pattern padrao;
	private DateFormat formatador;
	private String meses[] = { "Janeiro", "Fevereiro", "Marco", "Abril",
			"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
			"Novembro", "Dezembro" };

	public ManipularData() {
		localidade = new Locale("pt", "BR");
		calendario = Calendar.getInstance(localidade);
		padrao = Pattern.compile("dd/MM/yyyy");
	}

	public ManipularData(Date data) {
		localidade = new Locale("pt", "BR");
		calendario = Calendar.getInstance(localidade);
		padrao = Pattern.compile("dd/MM/yyyy");
		calendario.setTime(data);
	}

	public ManipularData(Date data, Locale localidade) {
		this.localidade = localidade;
		calendario = Calendar.getInstance(this.localidade);
		padrao = Pattern.compile("dd/MM/yyyy");
		calendario.setTime(data);
		this.localidade = localidade;
	}

	public ManipularData(Calendar calendario) {
		localidade = new Locale("pt", "BR");
		this.calendario = Calendar.getInstance(localidade);
		padrao = Pattern.compile("dd/MM/yyyy");
		this.calendario = calendario;
	}

	public ManipularData(Locale localidade) {
		this.localidade = new Locale("pt", "BR");
		calendario = Calendar.getInstance(this.localidade);
		padrao = Pattern.compile("dd/MM/yyyy");
		this.localidade = localidade;
	}

	public ManipularData(Locale localidade, Calendar calendario) {
		this.localidade = new Locale("pt", "BR");
		this.calendario = Calendar.getInstance(this.localidade);
		padrao = Pattern.compile("dd/MM/yyyy");
		this.calendario = calendario;
		this.localidade = localidade;
	}

	public ManipularData(int dia, int mes, int ano) {
		localidade = new Locale("pt", "BR");
		calendario = Calendar.getInstance(localidade);
		padrao = Pattern.compile("dd/MM/yyyy");
		calendario.set(5, dia);
		calendario.set(2, mes - 1);
		calendario.set(1, ano);
		calendario.set(10, 0);
		calendario.set(12, 0);
		calendario.set(13, 0);
		calendario.set(14, 0);
	}

	public boolean dataEhValida() {
		Date data = null;
		String padrao = "dd/MM/yyyy";
		String dataTexto = paraPadrao(padrao);
		SimpleDateFormat format = new SimpleDateFormat(padrao);
		format.setLenient(false);
		try {
			data = format.parse(dataTexto);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public void adicionarDias(int numeroDias) {
		calendario.add(Calendar.DAY_OF_MONTH, Math.abs(numeroDias));
	}

	public void removerDias(int numeroDias) {
		calendario.add(Calendar.DAY_OF_MONTH, -Math.abs(numeroDias));
	}

	public void adicionarHoras(int numeroHoras) {
		calendario.add(Calendar.HOUR, Math.abs(numeroHoras));
	}

	public void removerHoras(int numeroHoras) {
		calendario.add(Calendar.HOUR, -Math.abs(numeroHoras));
	}

	public void adicionarMinutos(int numeroMinutos) {
		calendario.add(Calendar.MINUTE, Math.abs(numeroMinutos));
	}

	public void removerMinutos(int numeroMinutos) {
		calendario.add(Calendar.MINUTE, -Math.abs(numeroMinutos));
	}

	public String paraPadrao(String padrao) {
		String saidaComPadrao = "";
		formatador = new SimpleDateFormat(padrao);
		saidaComPadrao = formatador.format(getData());
		return saidaComPadrao;
	}

	public String paraPadrao(Pattern padrao) {
		String saidaComPadrao = "";
		formatador = new SimpleDateFormat(padrao.pattern());
		saidaComPadrao = formatador.format(getData());
		return saidaComPadrao;
	}

	public int paraRepresentacaoNumerica() {
		String str_data = (new StringBuilder(String.valueOf(calendario.get(1))))
				.append(calendario.get(2)).append(calendario.get(5)).toString();
		int representacao = Integer.parseInt(str_data.trim());
		return representacao;
	}

	public int paraRepresentacaoNumerica(Calendar calendario) {
		String str_data = (new StringBuilder(String.valueOf(calendario.get(1))))
				.append(calendario.get(2)).append(calendario.get(5)).toString();
		int representacao = Integer.parseInt(str_data.trim());
		return representacao;
	}

	public int paraRepresentacaoNumerica(Date data) {
		ManipularData temp = new ManipularData(data);
		String str_data = (new StringBuilder(String.valueOf(temp.calendario
				.get(1)))).append(temp.calendario.get(2))
				.append(temp.calendario.get(5)).toString();

		// Calendar tmcalendario = Calendar.getInstance(getLocalidade());
		// tmcalendario.setTime(data);
		// String str_data = (new
		// StringBuilder(String.valueOf(tmcalendario.get(1)))).append(tmcalendario.get(2)).append(tmcalendario.get(5)).toString();
		int representacao = Integer.parseInt(str_data.trim());
		return representacao;
	}

	public long paraRepresentacaoNumericaCompleta() {
		String srt_data = (new StringBuilder(String.valueOf(calendario.get(1))))
				.append(calendario.get(2)).append(calendario.get(5))
				.append(calendario.get(10)).append(calendario.get(12))
				.append(calendario.get(13)).append(calendario.get(14))
				.toString();
		long representacao = Long.parseLong(srt_data.trim());
		return representacao;
	}

	public long paraRepresentacaoNumericaCompleta(Calendar calendario) {
		String srt_data = (new StringBuilder(String.valueOf(calendario.get(1))))
				.append(calendario.get(2)).append(calendario.get(5))
				.append(calendario.get(10)).append(calendario.get(12))
				.append(calendario.get(13)).append(calendario.get(14))
				.toString();
		long representacao = Long.parseLong(srt_data.trim());
		return representacao;
	}

	public long paraRepresentacaoNumericaCompleta(Date data) {
		Calendar tmcalendario = Calendar.getInstance(getLocalidade());
		tmcalendario.setTime(data);
		String srt_data = (new StringBuilder(
				String.valueOf(tmcalendario.get(1))))
				.append(tmcalendario.get(2)).append(tmcalendario.get(5))
				.append(tmcalendario.get(10)).append(tmcalendario.get(12))
				.append(tmcalendario.get(13)).append(tmcalendario.get(14))
				.toString();
		long representacao = Long.parseLong(srt_data.trim());
		return representacao;
	}

	public boolean souMaior(Date data) {
		boolean souMaior = false;
		int representacaoAtiva = paraRepresentacaoNumerica();
		int representacaoParametro = paraRepresentacaoNumerica(data);
		if (representacaoAtiva > representacaoParametro)
			souMaior = true;
		return souMaior;
	}

	public boolean souMaiorOuIgual(Date data) {
		boolean souMaior = false;
		int representacaoAtiva = paraRepresentacaoNumerica();
		int representacaoParametro = paraRepresentacaoNumerica(data);
		if (representacaoAtiva >= representacaoParametro)
			souMaior = true;
		return souMaior;
	}

	public boolean souMenor(Date data) {
		boolean souMaior = false;
		int representacaoAtiva = paraRepresentacaoNumerica();
		int representacaoParametro = paraRepresentacaoNumerica(data);
		if (representacaoAtiva < representacaoParametro)
			souMaior = true;
		return souMaior;
	}

	public boolean souMenorOuIgual(Date data) {
		boolean souMaior = false;
		int representacaoAtiva = paraRepresentacaoNumerica();
		int representacaoParametro = paraRepresentacaoNumerica(data);
		if (representacaoAtiva <= representacaoParametro)
			souMaior = true;
		return souMaior;
	}

	public boolean souEquivalente(Date data) {
		boolean souEquivalente = false;
		int representacaoAtiva = paraRepresentacaoNumerica();
		int representacaoParametro = paraRepresentacaoNumerica(data);
		if (representacaoAtiva == representacaoParametro)
			souEquivalente = true;
		return souEquivalente;
	}

	public boolean souIgual(Date data) {
		boolean souIgual = false;
		long representacaoAtiva = paraRepresentacaoNumericaCompleta();
		long representacaoParametro = paraRepresentacaoNumericaCompleta(data);
		if (representacaoAtiva == representacaoParametro)
			souIgual = true;
		return souIgual;
	}

	public long diferencaEmDiasUteis(Date date) {
		Calendar tmp = Calendar.getInstance(new Locale("pt", "BR"));
		tmp.set(GregorianCalendar.HOUR, 0);
		tmp.set(GregorianCalendar.MINUTE, 0);
		tmp.set(GregorianCalendar.SECOND, 0);
		Calendar tmp1 = this.calendario;
		tmp1.setTime(date);
		tmp1.set(GregorianCalendar.HOUR, 0);
		tmp1.set(GregorianCalendar.MINUTE, 0);
		tmp1.set(GregorianCalendar.SECOND, 1);

		int dif = 0;
		while (tmp.compareTo(tmp1) > 0) {
			tmp.add(GregorianCalendar.DAY_OF_MONTH, -1);
			if (tmp.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY
					&& tmp.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY) {
				dif++;
			}
			if (dif >= 70)
				break;
		}
		return dif;
	}

	public long diferencaEmDias(Date date) {
		Calendar tmp = Calendar.getInstance(new Locale("pt", "BR"));
		tmp.setTime(date);
		long diff = (tmp.getTime().getTime() - getData().getTime()) / 0x5265c00L;
		return diff;
	}

	public long diferencaEmHoras(Date date) {
		Calendar tmp = Calendar.getInstance(new Locale("pt", "BR"));
		tmp.setTime(date);
		long diffDias = (tmp.getTime().getTime() - getData().getTime()) / 0x5265c00L;
		long diffHoras = diffDias * 24L;
		return diffHoras;
	}

	public Integer numeroDeDiasDoMes() {
		return Integer.valueOf(calendario.getActualMaximum(5));
	}

	public String nomeDoMes() {
		return meses[calendario.get(2)];
	}

	public String nomeDoMes(Integer mes) {
		setMes(mes.intValue());
		return meses[calendario.get(2)];
	}

	public Pattern getPadrao() {
		return padrao;
	}

	public void setPadrao(Pattern padrao) {
		this.padrao = padrao;
	}

	public Calendar getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendar calendario) {
		this.calendario = calendario;
	}

	public Locale getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Locale localidade) {
		this.localidade = localidade;
	}

	public Timestamp getDataTimestamp() {
		return new Timestamp((calendario.getTime()).getTime());
	}

	public Date getData() {
		return calendario.getTime();
	}

	public void setData() {
		calendario.setTime(new Date(System.currentTimeMillis()));
	}

	public void setData(Date date) {
		calendario.setTime(date);
	}

	public void setData(int dia, int mes, int ano) {
		calendario.set(5, dia);
		calendario.set(2, mes - 1);
		calendario.set(1, ano);
		calendario.set(10, 0);
		calendario.set(12, 0);
		calendario.set(13, 0);
		calendario.set(14, 0);
	}

	public int getDia() {
		return calendario.get(5);
	}

	public void setDia(int dia) {
		calendario.set(5, Math.abs(dia));
	}

	public int getMes() {
		return calendario.get(2) + 1;
	}

	public void setMes(int mes) {
		calendario.set(2, Math.abs(mes - 1));
	}

	public void setMes(String nomeMes) {
		int tamanho = meses.length;
		int mes = 0;
		for (int i = 0; i < tamanho; i++) {
			if (!meses[i].equals(nomeMes))
				continue;
			mes = i;
			break;
		}

		calendario.set(2, Math.abs(mes));
	}

	public int getAno() {
		return calendario.get(1);
	}

	public void setAno(int ano) {
		calendario.set(1, Math.abs(ano));
	}

}