package com.yangcao.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.yangcao.simpleresume.model.BasicInfo;
import com.yangcao.simpleresume.model.Education;
import com.yangcao.simpleresume.model.Experience;
import com.yangcao.simpleresume.model.Project;
import com.yangcao.simpleresume.util.DateUtils;
import com.yangcao.simpleresume.util.ImageUtils;
import com.yangcao.simpleresume.util.ModelUtils;

import org.w3c.dom.Text;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_BASIC_INFO_EDIT = 100;
    private static final int REQ_CODE_EDUCATION_EDIT = 101;
    private static final int REQ_CODE_EXPERIENCE_EDIT = 102;
    private static final int REQ_CODE_PROJECT_EDIT = 103;

    private static final String MODEL_BASIC_INFO = "basic_info";
    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        setupUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_BASIC_INFO_EDIT:
                    basicInfo =
                            data.getParcelableExtra(BasicInfoEditActivity.KEY_BASIC_INFO);
                    updateBasicInfo(basicInfo);
                    break;

                case REQ_CODE_EDUCATION_EDIT:
                    String educationId = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                    if (educationId != null) {
                        deleteEducation(educationId);
                    } else{
                        Education education =
                                data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION); //根据key取出对应result
                        updateEducation(education);
                    }
                    break;

                case REQ_CODE_EXPERIENCE_EDIT:
                    String experienceId = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
                    if (experienceId != null) {
                        deleteExperience(experienceId);
                    } else {
                        Experience experience =
                                data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                        updateExperience(experience);
                    }
                    break;

                case REQ_CODE_PROJECT_EDIT:
                    String projectId = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                    if (projectId != null) {
                        deleteProject(projectId);
                    } else {
                        Project project =
                                data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                        updateProject(project);
                    }
                    break;
            }
        }

    }


    private void setupUI() {
        setContentView(R.layout.activity_main);

        setupBasicInfoUI();
        setupEducations();
        setupExperiences();
        setupProjects();

        //click add btn to launch education_edit_activity
        findViewById(R.id.add_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT); //link this req_code to the activity
            }
        });

        //click add btn to launch experience_edit_activity
        findViewById(R.id.add_experience_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        //click add btn to launch project_edit_activity
        findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });

    }


    private void setupBasicInfoUI() {
        ((TextView) findViewById(R.id.name)).setText(TextUtils.isEmpty(basicInfo.name)
                ? "Your Name"
                : basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(TextUtils.isEmpty(basicInfo.email)
                ? "Your Email"
                : basicInfo.email);

        // setup user image
        ImageView user_picture = (ImageView)findViewById(R.id.user_picture);
        if (basicInfo.imageUri != null) {
            ImageUtils.loadImage(this, basicInfo.imageUri, user_picture); //bitmap
        } else {
            user_picture.setImageResource(R.drawable.ic_user_ghost);
        }

        //click edit btn to launch basic_info_edit_activity
        findViewById(R.id.edit_basic_info_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_BASIC_INFO_EDIT);
            }
        });

    }

    private void updateBasicInfo(BasicInfo newBasicInfo) {
        ModelUtils.saveModel(this, MODEL_BASIC_INFO, newBasicInfo);
        this.basicInfo = newBasicInfo; // update current basicInfo
        setupBasicInfoUI();
    }

    private void setupEducations() {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.educations);
        educationsLayout.removeAllViews();
        for (Education education : educations) {
            View educationView = getLayoutInflater().inflate(R.layout.education_item, null);//convert layout file to view
            setupEducation(educationView, education);
            educationsLayout.addView(educationView);
        }
    }

    private void updateEducation(Education newEducation) {
        boolean found = false;
        for (int i = 0; i < educations.size(); i++) { //判断这个education在当前界面中有没有
            Education item = educations.get(i);
            if (TextUtils.equals(item.id, newEducation.id)) {
                educations.set(i, newEducation);
                found = true;
                break;
            }
        }
        if (!found) {
            educations.add(newEducation);
        }

        ModelUtils.saveModel(this, MODEL_EDUCATIONS, educations); //store to disk
        setupEducations();
    }

    private void deleteEducation(@NonNull String educationId) {
        for (int i = 0; i < educations.size(); i++) {
            Education item = educations.get(i);
            if (TextUtils.equals(educationId, item.id)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.saveModel(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void setupEducation(View educationView, final Education education) {
        String dateString = DateUtils.dateToString(education.startDate)
                + " ~ " + DateUtils.dateToString(education.endDate);
        ((TextView)educationView.findViewById(R.id.education_school))
                .setText(education.school + " - " + education.major + " (" + dateString + ")");
        ((TextView)educationView.findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));

        // click edit btn to edit
        educationView.findViewById((R.id.edit_education_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education); //传入初值
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
    }

    private void setupExperiences() {
        LinearLayout experiencesLayout = (LinearLayout) findViewById(R.id.experiences);
        experiencesLayout.removeAllViews();
        for (Experience experience : experiences) {
            View experienceView = getLayoutInflater().inflate(R.layout.experience_item, null);
            setupExperience(experienceView, experience);
            experiencesLayout.addView(experienceView);
        }
    }

    private void updateExperience(Experience newExperience) {
        boolean found = false;
        for (int i = 0; i < experiences.size(); i++) {
            if (TextUtils.equals(newExperience.id, experiences.get(i).id)) {
                experiences.set(i, newExperience);
                found = true;
                break;
            }
        }
        if (!found) {
            experiences.add(newExperience);
        }

        ModelUtils.saveModel(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void deleteExperience(@NonNull String experienceId) {
        for (int i = 0; i < experiences.size(); i++) {
            Experience item = experiences.get(i);
            if (TextUtils.equals(experienceId, item.id)) {
                experiences.remove(i);
                break;
            }
        }
        ModelUtils.saveModel(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void setupExperience(View experienceView, final Experience experience) {
        String dateString = DateUtils.dateToString(experience.startDate)
                + " ~ " + DateUtils.dateToString(experience.endDate);
        ((TextView)experienceView.findViewById(R.id.experience_company))
                .setText(experience.company + " - " + experience.title + " (" + dateString + ")");
        ((TextView)experienceView.findViewById(R.id.experience_work))
                .setText(formatItems(experience.work));

        //click experience_edit btn to edit
        experienceView.findViewById(R.id.edit_experience_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, experience);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

    }


    private void setupProjects() {
        LinearLayout projectsLayout = (LinearLayout) findViewById(R.id.projects);
        projectsLayout.removeAllViews();
        for (Project project : projects) {
            View projectView = getLayoutInflater().inflate(R.layout.project_item, null);
            setupProject(projectView, project);
            projectsLayout.addView(projectView);
        }
    }

    private void updateProject(Project newProject) {
        boolean found = false;
        for (int i = 0; i < projects.size(); i++) {
            if (TextUtils.equals(newProject.id, projects.get(i).id)) {
                projects.set(i, newProject);
                found = true;
                break;
            }
        }
        if (!found) {
            projects.add(newProject);
        }

        ModelUtils.saveModel(this, MODEL_PROJECTS, projects);
        setupProjects();
    }

    private void deleteProject(@NonNull String projectId) {
        for (int i = 0; i < projects.size(); i++) {
            Project item = projects.get(i);
            if (TextUtils.equals(projectId, item.id)) {
                projects.remove(i);
                break;
            }
        }
        ModelUtils.saveModel(this, MODEL_PROJECTS, projects);
        setupProjects();
    }


    private void setupProject(View projectView, final Project project) {
        String dateString = DateUtils.dateToString(project.startDate)
                + " ~ " + DateUtils.dateToString(project.endDate);
        ((TextView)projectView.findViewById(R.id.project_name))
                .setText(project.name + " (" + dateString  + ")");
        ((TextView)projectView.findViewById(R.id.project_details))
                .setText(formatItems(project.details));

        //click edit btn to edit project item
        projectView.findViewById(R.id.edit_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });
    }


    //load data from disk, no fake
    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.readModel(
                this, MODEL_BASIC_INFO, new TypeToken<BasicInfo>(){});
        basicInfo = savedBasicInfo == null ? new BasicInfo() : savedBasicInfo;

        List<Education> savedEducations = ModelUtils.readModel(
                this, MODEL_EDUCATIONS, new TypeToken<List<Education>>(){});
        educations = savedEducations == null ? new ArrayList<Education>() : savedEducations;

        List<Experience> savedExperiences = ModelUtils.readModel(
                this, MODEL_EXPERIENCES, new TypeToken<List<Experience>>(){});
        experiences = savedExperiences == null ? new ArrayList<Experience>() : savedExperiences;

        List<Project> savedProjects = ModelUtils.readModel(
                this, MODEL_PROJECTS, new TypeToken<List<Project>>(){});
        projects = savedProjects == null ? new ArrayList<Project>() : savedProjects;
    }


    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


}
