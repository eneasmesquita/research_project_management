package br.projetos.propes.mail;

public class MailNovoProjeto {

    //ESSA CLASSE UTILIZA OS MÉTODOS DA CLASSE SendMail.java
    private static String mensagemEmail = "<!DOCTYPE html>\n"
            + "\n"
            + "<html>\n"
            + "    <head>\n"
            + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
            + "\n"
            + "        <!-- Facebook sharing information tags -->\n"
            + "        <meta property=\"og:title\" content=\"*|MC:SUBJECT|*\" />\n"
            + "\n"
            + "        <title>mailNovoProjeto</title>\n"
            + "        <style type=\"text/css\">\n"
            + "            /* Client-specific Styles */\n"
            + "            #outlook a{padding:0;} /* Force Outlook to provide a \"view in browser\" button. */\n"
            + "            body{width:100% !important;} .ReadMsgBody{width:100%;} .ExternalClass{width:100%;} /* Force Hotmail to display emails at full width */\n"
            + "            body{-webkit-text-size-adjust:none;} /* Prevent Webkit platforms from changing default text sizes. */\n"
            + "            /* Reset Styles */\n"
            + "            body{margin:0; padding:0;}\n"
            + "            img{border:0; height:auto; line-height:100%; outline:none; text-decoration:none;}\n"
            + "            table td{border-collapse:collapse;}\n"
            + "            #backgroundTable{height:100% !important; margin:0; padding:0; width:100% !important;}\n"
            + "            /* Template Styles */\n"
            + "            /* /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ STANDARD STYLING: COMMON PAGE ELEMENTS /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ */\n"
            + "            /**\n"
            + "            * @tab Page\n"
            + "            * @section background color\n"
            + "            * @tip Set the background color for your email. You may want to choose one that matches your company's branding.\n"
            + "            * @theme page\n"
            + "            */\n"
            + "            body, #backgroundTable{\n"
            + "                /*@editable*/ background-color:#FAFAFA;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Page\n"
            + "            * @section email border\n"
            + "            * @tip Set the border for your email.\n"
            + "            */\n"
            + "            #templateContainer{\n"
            + "                /*@editable*/ border: 1px solid #DDDDDD;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Page\n"
            + "            * @section heading 1\n"
            + "            * @tip Set the styling for all first-level headings in your emails. These should be the largest of your headings.\n"
            + "            * @style heading 1\n"
            + "            */\n"
            + "            h1, .h1{\n"
            + "                /*@editable*/ color:#202020;\n"
            + "                display:block;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:34px;\n"
            + "                /*@editable*/ font-weight:bold;\n"
            + "                /*@editable*/ line-height:100%;\n"
            + "                margin-top:0;\n"
            + "                margin-right:0;\n"
            + "                margin-bottom:10px;\n"
            + "                margin-left:0;\n"
            + "                /*@editable*/ text-align:left;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Page\n"
            + "            * @section heading 2\n"
            + "            * @tip Set the styling for all second-level headings in your emails.\n"
            + "            * @style heading 2\n"
            + "            */\n"
            + "            h2, .h2{\n"
            + "                /*@editable*/ color:#202020;\n"
            + "                display:block;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:30px;\n"
            + "                /*@editable*/ font-weight:bold;\n"
            + "                /*@editable*/ line-height:100%;\n"
            + "                margin-top:0;\n"
            + "                margin-right:0;\n"
            + "                margin-bottom:10px;\n"
            + "                margin-left:0;\n"
            + "                /*@editable*/ text-align:left;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Page\n"
            + "            * @section heading 3\n"
            + "            * @tip Set the styling for all third-level headings in your emails.\n"
            + "            * @style heading 3\n"
            + "            */\n"
            + "            h3, .h3{\n"
            + "                /*@editable*/ color:#202020;\n"
            + "                display:block;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:26px;\n"
            + "                /*@editable*/ font-weight:bold;\n"
            + "                /*@editable*/ line-height:100%;\n"
            + "                margin-top:0;\n"
            + "                margin-right:0;\n"
            + "                margin-bottom:10px;\n"
            + "                margin-left:0;\n"
            + "                /*@editable*/ text-align:left;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Page\n"
            + "            * @section heading 4\n"
            + "            * @tip Set the styling for all fourth-level headings in your emails. These should be the smallest of your headings.\n"
            + "            * @style heading 4\n"
            + "            */\n"
            + "            h4, .h4{\n"
            + "                /*@editable*/ color:#202020;\n"
            + "                display:block;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:22px;\n"
            + "                /*@editable*/ font-weight:bold;\n"
            + "                /*@editable*/ line-height:100%;\n"
            + "                margin-top:0;\n"
            + "                margin-right:0;\n"
            + "                margin-bottom:10px;\n"
            + "                margin-left:0;\n"
            + "                /*@editable*/ text-align:left;\n"
            + "            }\n"
            + "            /* /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ STANDARD STYLING: HEADER /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ */\n"
            + "            /**\n"
            + "            * @tab Header\n"
            + "            * @section header style\n"
            + "            * @tip Set the background color and border for your email's header area.\n"
            + "            * @theme header\n"
            + "            */\n"
            + "            #templateHeader{\n"
            + "                /*@editable*/ background-color:#FFFFFF;\n"
            + "                /*@editable*/ border-bottom:0;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Header\n"
            + "            * @section header text\n"
            + "            * @tip Set the styling for your email's header text. Choose a size and color that is easy to read.\n"
            + "            */\n"
            + "            .headerContent{\n"
            + "                /*@editable*/ color:#202020;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:34px;\n"
            + "                /*@editable*/ font-weight:bold;\n"
            + "                /*@editable*/ line-height:100%;\n"
            + "                /*@editable*/ padding:0;\n"
            + "                /*@editable*/ text-align:center;\n"
            + "                /*@editable*/ vertical-align:middle;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Header\n"
            + "            * @section header link\n"
            + "            * @tip Set the styling for your email's header links. Choose a color that helps them stand out from your text.\n"
            + "            */\n"
            + "            .headerContent a:link, .headerContent a:visited, /* Yahoo! Mail Override */ .headerContent a .yshortcuts /* Yahoo! Mail Override */{\n"
            + "                /*@editable*/ color:#336699;\n"
            + "                /*@editable*/ font-weight:normal;\n"
            + "                /*@editable*/ text-decoration:underline;\n"
            + "            }\n"
            + "            #headerImage{\n"
            + "                height:auto;\n"
            + "                max-width:600px !important;\n"
            + "            }\n"
            + "            /* /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ STANDARD STYLING: MAIN BODY /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ */\n"
            + "            /**\n"
            + "            * @tab Body\n"
            + "            * @section body style\n"
            + "            * @tip Set the background color for your email's body area.\n"
            + "            */\n"
            + "            #templateContainer, .bodyContent{\n"
            + "                /*@editable*/ background-color:#FFFFFF;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Body\n"
            + "            * @section body text\n"
            + "            * @tip Set the styling for your email's main content text. Choose a size and color that is easy to read.\n"
            + "            * @theme main\n"
            + "            */\n"
            + "            .bodyContent div{\n"
            + "                /*@editable*/ color:#505050;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:14px;\n"
            + "                /*@editable*/ line-height:150%;\n"
            + "                /*@editable*/ text-align:left;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Body\n"
            + "            * @section body link\n"
            + "            * @tip Set the styling for your email's main content links. Choose a color that helps them stand out from your text.\n"
            + "            */\n"
            + "            .bodyContent div a:link, .bodyContent div a:visited, /* Yahoo! Mail Override */ .bodyContent div a .yshortcuts /* Yahoo! Mail Override */{\n"
            + "                /*@editable*/ color:#336699;\n"
            + "                /*@editable*/ font-weight:normal;\n"
            + "                /*@editable*/ text-decoration:underline;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Body\n"
            + "            * @section button style\n"
            + "            * @tip Set the styling for your email's button. Choose a style that draws attention.\n"
            + "            */\n"
            + "            .templateButton{\n"
            + "                -moz-border-radius:3px;\n"
            + "                -webkit-border-radius:3px;\n"
            + "                /*@editable*/ background-color:#336699;\n"
            + "                /*@editable*/ border:0;\n"
            + "                border-collapse:separate !important;\n"
            + "                border-radius:3px;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Body\n"
            + "            * @section button style\n"
            + "            * @tip Set the styling for your email's button. Choose a style that draws attention.\n"
            + "            */\n"
            + "            .templateButton, .templateButton a:link, .templateButton a:visited, /* Yahoo! Mail Override */ .templateButton a .yshortcuts /* Yahoo! Mail Override */{\n"
            + "                /*@editable*/ color:#FFFFFF;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:12px;\n"
            + "                /*@editable*/ font-weight:bold;\n"
            + "                /*@editable*/ letter-spacing:-.5px;\n"
            + "                /*@editable*/ line-height:100%;\n"
            + "                text-align:center;\n"
            + "                text-decoration:none;\n"
            + "            }\n"
            + "            .bodyContent img{\n"
            + "                display:inline;\n"
            + "                height:auto;\n"
            + "            }\n"
            + "            /* /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ STANDARD STYLING: FOOTER /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ */\n"
            + "            /**\n"
            + "            * @tab Footer\n"
            + "            * @section footer style\n"
            + "            * @tip Set the background color and top border for your email's footer area.\n"
            + "            * @theme footer\n"
            + "            */\n"
            + "            #templateFooter{\n"
            + "                /*@editable*/ background-color:#FFFFFF;\n"
            + "                /*@editable*/ border-top:0;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Footer\n"
            + "            * @section footer text\n"
            + "            * @tip Set the styling for your email's footer text. Choose a size and color that is easy to read.\n"
            + "            * @theme footer\n"
            + "            */\n"
            + "            .footerContent div{\n"
            + "                /*@editable*/ color:#707070;\n"
            + "                /*@editable*/ font-family:Arial;\n"
            + "                /*@editable*/ font-size:12px;\n"
            + "                /*@editable*/ line-height:125%;\n"
            + "                /*@editable*/ text-align:center;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Footer\n"
            + "            * @section footer link\n"
            + "            * @tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n"
            + "            */\n"
            + "            .footerContent div a:link, .footerContent div a:visited, /* Yahoo! Mail Override */ .footerContent div a .yshortcuts /* Yahoo! Mail Override */{\n"
            + "                /*@editable*/ color:#336699;\n"
            + "                /*@editable*/ font-weight:normal;\n"
            + "                /*@editable*/ text-decoration:underline;\n"
            + "            }\n"
            + "            .footerContent img{\n"
            + "                display:inline;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Footer\n"
            + "            * @section utility bar style\n"
            + "            * @tip Set the background color and border for your email's footer utility bar.\n"
            + "            * @theme footer\n"
            + "            */\n"
            + "            #utility{\n"
            + "                /*@editable*/ background-color:#FFFFFF;\n"
            + "                /*@editable*/ border:0;\n"
            + "            }\n"
            + "            /**\n"
            + "            * @tab Footer\n"
            + "            * @section utility bar style\n"
            + "            * @tip Set the background color and border for your email's footer utility bar.\n"
            + "            */\n"
            + "            #utility div{\n"
            + "                /*@editable*/ text-align:center;\n"
            + "            }\n"
            + "            #monkeyRewards img{\n"
            + "                max-width:190px;\n"
            + "            }\n"
            + "        </style>\n"
            + "    </head>\n"
            + "    <body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\">\n"
            + "    <center>\n"
            + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"backgroundTable\">\n"
            + "            <tr>\n"
            + "                <td align=\"center\" valign=\"top\" style=\"padding-top:20px;\">\n"
            + "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateContainer\">\n"
            + "                        <tr>\n"
            + "                            <td align=\"center\" valign=\"top\">\n"
            + "                                <!-- // Begin Template Header \\\\ -->\n"
            + "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateHeader\">\n"
            + "                                    <tr>\n"
            + "                                        <td class=\"headerContent\">\n"
            + "\n"
            + "                                            <!-- // Begin Module: Standard Header Image \\\\ -->\n"
            + "                                            <img src=\"http://i.imgsafe.org/2f67c626f4.jpg\" width=\"600\" height=\"150\" alt=\"banner3\"/>\n"
            + "                                            <!-- // End Module: Standard Header Image \\\\ -->\n"
            + "\n"
            + "                                        </td>\n"
            + "                                    </tr>\n"
            + "                                </table>\n"
            + "                                <!-- // End Template Header \\\\ -->\n"
            + "                            </td>\n"
            + "                        </tr>\n"
            + "                        <tr>\n"
            + "                            <td align=\"center\" valign=\"top\">\n"
            + "                                <!-- // Begin Template Body \\\\ -->\n"
            + "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateBody\">\n"
            + "                                    <tr>\n"
            + "                                        <td valign=\"top\">\n"
            + "\n"
            + "                                            <!-- // Begin Module: Standard Content \\\\ -->\n"
            + "                                            <table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">\n"
            + "                                                <tr>\n"
            + "                                                    <td valign=\"top\" class=\"bodyContent\">\n"
            + "                                                        <div mc:edit=\"std_content00\">\n"
            + "                                                            <h3 class=\"h3\">A Plataforma Integrada de Projetos de Pesquisa informa:</h3>\n"
            + "                                                            <br/>\n"
            + "                                                            Novo Projeto com título <strong>[TITULO_DO_PROJETO]</strong><br/> e nº <strong>[NUMERO_PROJETO]</strong> submetido pelo aluno <strong>[NOME_ALUNO]</strong> do curso de <strong>[CURSO]</strong> encontra-se disponível para avaliação desta Pró-Reitoria de Pesquisa e Pós-Graduação.\n"
            + "                                                            <br />\n"
            + "                                                            <br />\n"
            + "                                                            <br />\n"
            + "                                                        </div>\n"
            + "                                                    </td>\n"
            + "                                                </tr>\n"
            + "                                                <tr>\n"
            + "                                                    <td align=\"center\" valign=\"top\" style=\"padding-top:0;\">\n"
            + "                                                        <table border=\"0\" cellpadding=\"15\" cellspacing=\"0\" class=\"templateButton\">\n"
            + "                                                            <tr>\n"
            + "                                                                <td valign=\"middle\" class=\"templateButtonContent\">\n"
            + "                                                                    <div mc:edit=\"std_content01\">\n"
            + "                                                                        Este e-mail é automático e não precisa ser respondido\n"
            + "                                                                    </div>\n"
            + "                                                                </td>\n"
            + "                                                            </tr>\n"
            + "                                                        </table>\n"
            + "                                                    </td>\n"
            + "                                                </tr>\n"
            + "                                            </table>\n"
            + "                                            <!-- // End Module: Standard Content \\\\ -->\n"
            + "\n"
            + "                                        </td>\n"
            + "                                    </tr>\n"
            + "                                </table>\n"
            + "                                <!-- // End Template Body \\\\ -->\n"
            + "                            </td>\n"
            + "                        </tr>\n"
            + "                        <tr>\n"
            + "                            <td align=\"center\" valign=\"top\">\n"
            + "                                <!-- // Begin Template Footer \\\\ -->\n"
            + "                                <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"templateFooter\">\n"
            + "                                    <tr>\n"
            + "                                        <td valign=\"top\" class=\"footerContent\">\n"
            + "\n"
            + "                                            <!-- // Begin Module: Transactional Footer \\\\ -->\n"
            + "                                            <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n"
            + "                                                <tr>\n"
            + "                                                    <td valign=\"top\">\n"
            + "                                                        <div mc:edit=\"std_footer\">\n"
            + "                                                            <em>Copyright &copy; <strong>[ANO]</strong> Universidade Estadual de Roraima, Todos os direitos reservados.</em>\n"
            + "                                                            <br />\n"
            + "                                                            Pró-Reitoria de Pesquisa e Pós-Graduação\n"
            + "                                                            <br />\n"
            + "                                                            <a href=\"mailto:propes@universidade.edu.br?subject=PLATAFORMA INTEGRADA DE PROJETOS - INTERAÇÃO DO ALUNO\" style=\"color: #3399ff\">propes@universidade.edu.br</a>\n"
            + "                                                        </div>\n"
            + "                                                    </td>\n"
            + "                                                </tr>\n"
            + "                                            </table>\n"
            + "                                            <!-- // End Module: Transactional Footer \\\\ -->\n"
            + "\n"
            + "                                        </td>\n"
            + "                                    </tr>\n"
            + "                                </table>\n"
            + "                                <!-- // End Template Footer \\\\ -->\n"
            + "                            </td>\n"
            + "                        </tr>\n"
            + "                    </table>\n"
            + "                    <br />\n"
            + "                </td>\n"
            + "            </tr>\n"
            + "        </table>\n"
            + "    </center>\n"
            + "</body>\n"
            + "</html>\n"
            + "";

    public MailNovoProjeto() {
    }

    public static boolean enviarEmailNovoProjeto(String emailDestinatario, String tituloProjeto, String numeroProjeto, String nomeAluno, String curso, String ano) {

        SendMail mail = new SendMail();
        String remetente = "disparador.email@universidade.edu.br";
        String usuario = remetente;
        String senha = "6745@../#$75";
        String assunto = "PLATAFORMA DE PROJETOS DA PROPES - NOVO PROJETO CADASTRADO";

        String mensagemFinal = mensagemEmail;
        mensagemFinal = mensagemFinal.replace("[TITULO_DO_PROJETO]", tituloProjeto);
        mensagemFinal = mensagemFinal.replace("[NUMERO_PROJETO]", numeroProjeto);
        mensagemFinal = mensagemFinal.replace("[NOME_ALUNO]", nomeAluno);
        mensagemFinal = mensagemFinal.replace("[CURSO]", curso);
        mensagemFinal = mensagemFinal.replace("[ANO]", ano);

        try {
            return mail.sendMail(remetente, emailDestinatario, assunto, mensagemFinal, usuario, senha);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        MailNovoProjeto emailNovoProjeto = new MailNovoProjeto();
        emailNovoProjeto.enviarEmailNovoProjeto("responsavel@universidade.edu.br", "TESTANDO...", "01", "ALUNO FULANO DE TAL", "DIREITO", "2016");
    }

}
