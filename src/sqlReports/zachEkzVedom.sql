SELECT 
	stud.student_lastname AS Familiya,
	stud.student_name AS Imya,
	stud.student_middlename AS Otchestvo,
	uchpl.student_id AS ID,
	uchpl.group_code AS GroupCode,
	uchpl.uchpls_ball AS ball,
	uchpl.uchpls_ocenka AS Ocenka,
	uchpl.disc_code AS DiscCode,
	uchpl.uchplans_sem AS SemUchPlan,
	uchpl.uchpls_numbervedom AS vedomNum,
	sp_disc.disc_name AS DiscName,
	gr.group_semestr AS SemestrGroup,
	spr_formob.formobuch_name AS Formaobuchen,
	spr_ru.A_NAMEL AS BafeName,
	spr_ru.A_UOTDEL AS Uotdel,
	spr_ru.A_1 AS excellLow,
	spr_ru.A_2 AS ecxellTop,
	spr_ru.A_3 AS goodLow,
	spr_ru.A_4 AS goodTop,
	spr_ru.A_5 AS soLow,
	spr_ru.A_6 AS soTop,
	spr_ru.A_YEARS AS Year1,
	spr_ru.A_YEARE AS Year2
FROM 
uchplanstudents uchpl
INNER JOIN student stud ON uchpl.student_id=stud.student_id
INNER JOIN spr_discipline sp_disc ON uchpl.disc_code=sp_disc.disc_code
INNER JOIN groups gr ON uchpl.group_code=gr.group_code
INNER JOIN spr_formobuch spr_formob ON gr.group_formaobuch = spr_formob.formobuch_code,
spr_settings spr_ru
WHERE 
uchpl.disc_code='...'
AND
uchpl.group_code='...'
ORDER BY Familiya ASC