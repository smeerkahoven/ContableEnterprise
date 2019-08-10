update tb_formularios set restful_url = '/ContableEnterprise-ws/ws-api/parametros/' where url_acceso='parametros';

update tb_formularios set restful_url = replace (restful_url,'http://192.168.1.215:8080','');

select * from tb_formularios ;