SELECT
    upl.student_id AS st_ID,
    upl.uchplans_sem AS uchP_semestr,
    st.student_lastname AS Lastname,
    st.student_name AS Name,
    st.student_middlename AS Middlename,
    upl.group_code AS Gruppa,
    upl.disc_code AS Disc,
    sp_disc.disc_name AS disc_Name,
    sp_form.formobuch_name AS form_Name,
    sett_RU.A_NAMEL AS bafeName
FROM uchplanstudents upl
INNER JOIN student st ON upl.student_id=st.student_id
INNER JOIN groups gr ON upl.group_code=gr.group_code
INNER JOIN spr_formobuch sp_form ON gr.group_formaobuch=sp_form.formobuch_code
INNER JOIN spr_discipline sp_disc ON upl.disc_code=sp_disc.disc_code,
    spr_settings sett_RU
WHERE
    upl.student_id='...'
AND
    upl.disc_code='...'