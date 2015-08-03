SELECT
st.student_lastname AS student_student_lastname, 
st.student_name AS student_student_name,
st.student_middlename AS student_student_middlename,
departments.depart_name AS departments_depart_name,
uchplanstudents.group_code AS uchplanstudents_group_code,
uchplanstudents.student_id AS uchplanstudents_student_id,
uchplanstudents.uchPlans_sem AS uchplanstudents_uchPlans_sem,
uchplanstudents.disc_code AS uchplanstudents_disc_code,
uchplanstudents.uchpls_clock AS uchplanstudents_uchPls_clock,
uchplanstudents.uchpls_credit AS uchplanstudents_uchPls_credit,
uchplanstudents.uchpls_ball AS uchplanstudents_uchPls_ball,
uchplanstudents.uchpls_ocenka AS uchplanstudents_uchPls_ocenka,
uchplanstudents.uchpls_controlb AS uchplanstudents_uchPls_controlb,
uchplanstudents.uchpls_date AS uchplanstudents_uchPls_date,
uchplanstudents.uchpls_numbervedom AS VedomNumber,
groups.group_name AS groups_group_name,
st.student_id AS student_student_id,
spr_discipline.disc_name AS spr_discipline_disc_name,
groups.group_semestr AS groups_group_semestr,
spr_settings.A_NAMES AS spr_settings_A_NAMES
FROM
uchplanstudents uchplanstudents
INNER JOIN student st ON uchplanstudents.student_id = st.student_id
INNER JOIN groups groups ON st.group_code = groups.group_code AND groups.group_code = uchplanstudents.group_code
INNER JOIN departments departments ON groups.department_code = departments.depart_code
INNER JOIN spr_discipline spr_discipline ON uchplanstudents.disc_code = spr_discipline.disc_code,
spr_settings spr_settings
WHERE
uchplanstudents.student_id='.....'
AND
uchplanstudents.uchPlans_sem='.....'